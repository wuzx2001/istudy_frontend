package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.data.repository.UserRepository
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddStudentUiState(
    val username: String = "",
    val telephone: String = "",
    val age: String = "",
    val sex: SexEnum? = null,
    val grade: GradeEnum? = null,
    val password: String = "",
    
    // Dropdown states
    val sexDropdownExpanded: Boolean = false,
    val gradeDropdownExpanded: Boolean = false,
    
    // Validation errors
    val usernameError: String? = null,
    val telephoneError: String? = null,
    val ageError: String? = null,
    val sexError: String? = null,
    val gradeError: String? = null,
    val passwordError: String? = null,
    
    // UI states
    val isLoading: Boolean = false,
    val message: String? = null,
    val isFormValid: Boolean = false
)

sealed class AddStudentNavigationEvent {
    object NavigateBack : AddStudentNavigationEvent()
}

@HiltViewModel
class AddStudentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddStudentUiState())
    val uiState: StateFlow<AddStudentUiState> = _uiState.asStateFlow()
    
    private val _navigationEvent = Channel<AddStudentNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
        
        // Watch for form validation
        viewModelScope.launch {
            _uiState.collect { state ->
                val isValid = validateForm(state)
                if (state.isFormValid != isValid) {
                    _uiState.value = state.copy(isFormValid = isValid)
                }
            }
        }
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }
    
    // Form field updates
    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            usernameError = validateUsername(username)
        )
    }
    
    fun updateTelephone(telephone: String) {
        _uiState.value = _uiState.value.copy(
            telephone = telephone,
            telephoneError = validateTelephone(telephone)
        )
    }
    
    fun updateAge(age: String) {
        _uiState.value = _uiState.value.copy(
            age = age,
            ageError = validateAge(age)
        )
    }
    
    fun updateSex(sex: SexEnum) {
        _uiState.value = _uiState.value.copy(
            sex = sex,
            sexError = null
        )
    }
    
    fun updateGrade(grade: GradeEnum) {
        _uiState.value = _uiState.value.copy(
            grade = grade,
            gradeError = null
        )
    }
    
    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = validatePassword(password)
        )
    }
    
    // Dropdown state updates
    fun updateSexDropdownExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(sexDropdownExpanded = expanded)
    }
    
    fun updateGradeDropdownExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(gradeDropdownExpanded = expanded)
    }
    
    // Actions
    fun addStudent() {
        val state = _uiState.value
        if (!validateForm(state)) {
            _uiState.value = state.copy(message = "请检查输入信息")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, message = null)
            
            try {
                val request = StudentCreate(
                    username = state.username,
                    telephone = state.telephone,
                    age = state.age.toInt(),
                    sex = state.sex!!,
                    grade = state.grade!!,
                    password = state.password
                )
                
                val result = userRepository.createStudent(request)
                result.fold(
                    onSuccess = { response ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = "学生添加成功！"
                        )
                        // Navigate back after success
                        _navigationEvent.send(AddStudentNavigationEvent.NavigateBack)
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = error.message ?: "添加学生失败"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "添加学生失败：${e.message}"
                )
            }
        }
    }
    
    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.send(AddStudentNavigationEvent.NavigateBack)
        }
    }
    
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
    
    // Validation methods
    private fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> "请输入学生姓名"
            username.length > 50 -> "姓名不能超过50个字符"
            else -> null
        }
    }
    
    private fun validateTelephone(telephone: String): String? {
        return when {
            telephone.isBlank() -> "请输入学生手机号"
            telephone.length > 20 -> "手机号不能超过20个字符"
            !telephone.matches(Regex("^1[3-9]\\d{9}$")) -> "请输入有效的手机号"
            else -> null
        }
    }
    
    private fun validateAge(age: String): String? {
        return when {
            age.isBlank() -> "请输入学生年龄"
            age.toIntOrNull() == null -> "请输入有效的年龄"
            age.toInt() !in 0..150 -> "年龄必须在0-150之间"
            else -> null
        }
    }
    
    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "请输入学生密码"
            password.length < 6 -> "密码至少6个字符"
            else -> null
        }
    }
    
    private fun validateForm(state: AddStudentUiState): Boolean {
        return validateUsername(state.username) == null &&
                validateTelephone(state.telephone) == null &&
                validateAge(state.age) == null &&
                state.sex != null &&
                state.grade != null &&
                validatePassword(state.password) == null
    }
}
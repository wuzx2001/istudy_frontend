package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegistrationUiState(
    // Student Information
    val studentUsername: String = "",
    val studentTelephone: String = "",
    val studentAge: String = "",
    val studentSex: SexEnum? = null,
    val studentGrade: GradeEnum? = null,
    val studentPassword: String = "",
    
    // Parent Information
    val parentUsername: String = "",
    val parentTelephone: String = "",
    val parentType: UserTypeEnum? = null,
    val parentPassword: String = "",
    
    // Dropdown states
    val sexDropdownExpanded: Boolean = false,
    val gradeDropdownExpanded: Boolean = false,
    val parentTypeDropdownExpanded: Boolean = false,
    
    // Validation errors
    val studentUsernameError: String? = null,
    val studentTelephoneError: String? = null,
    val studentAgeError: String? = null,
    val studentSexError: String? = null,
    val studentGradeError: String? = null,
    val studentPasswordError: String? = null,
    val parentUsernameError: String? = null,
    val parentTelephoneError: String? = null,
    val parentTypeError: String? = null,
    val parentPasswordError: String? = null,
    
    // UI states
    val isLoading: Boolean = false,
    val message: String? = null,
    val isFormValid: Boolean = false
)

sealed class RegistrationNavigationEvent {
    object NavigateToStudentHome : RegistrationNavigationEvent()
    object NavigateToParentHome : RegistrationNavigationEvent()
    object NavigateToLogin : RegistrationNavigationEvent()
}

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()
    
    private val _navigationEvent = Channel<RegistrationNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()
    
    init {
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
    
    // Student Information Updates
    fun updateStudentUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            studentUsername = username,
            studentUsernameError = validateStudentUsername(username)
        )
    }
    
    fun updateStudentTelephone(telephone: String) {
        _uiState.value = _uiState.value.copy(
            studentTelephone = telephone,
            studentTelephoneError = validateStudentTelephone(telephone)
        )
    }
    
    fun updateStudentAge(age: String) {
        _uiState.value = _uiState.value.copy(
            studentAge = age,
            studentAgeError = validateStudentAge(age)
        )
    }
    
    fun updateStudentSex(sex: SexEnum) {
        _uiState.value = _uiState.value.copy(
            studentSex = sex,
            studentSexError = null
        )
    }
    
    fun updateStudentGrade(grade: GradeEnum) {
        _uiState.value = _uiState.value.copy(
            studentGrade = grade,
            studentGradeError = null
        )
    }
    
    fun updateStudentPassword(password: String) {
        _uiState.value = _uiState.value.copy(
            studentPassword = password,
            studentPasswordError = validateStudentPassword(password)
        )
    }
    
    // Parent Information Updates
    fun updateParentUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            parentUsername = username,
            parentUsernameError = validateParentUsername(username)
        )
    }
    
    fun updateParentTelephone(telephone: String) {
        _uiState.value = _uiState.value.copy(
            parentTelephone = telephone,
            parentTelephoneError = validateParentTelephone(telephone)
        )
    }
    
    fun updateParentType(type: UserTypeEnum) {
        _uiState.value = _uiState.value.copy(
            parentType = type,
            parentTypeError = null
        )
    }
    
    fun updateParentPassword(password: String) {
        _uiState.value = _uiState.value.copy(
            parentPassword = password,
            parentPasswordError = validateParentPassword(password)
        )
    }
    
    // Dropdown state updates
    fun updateSexDropdownExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(sexDropdownExpanded = expanded)
    }
    
    fun updateGradeDropdownExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(gradeDropdownExpanded = expanded)
    }
    
    fun updateParentTypeDropdownExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(parentTypeDropdownExpanded = expanded)
    }
    
    // Actions
    fun register() {
        val state = _uiState.value
        if (!validateForm(state)) {
            _uiState.value = state.copy(message = "请检查输入信息")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, message = null)
            
            try {
                val request = RegistrationRequest(
                    student_username = state.studentUsername,
                    student_telephone = state.studentTelephone,
                    student_age = state.studentAge.toInt(),
                    student_sex = state.studentSex!!,
                    student_grade = state.studentGrade!!,
                    student_password = state.studentPassword,
                    parent_username = state.parentUsername,
                    parent_telephone = state.parentTelephone,
                    parent_password = state.parentPassword,
                    parent_type = state.parentType!!.value // Use .value to get integer
                )
                
                val result = authRepository.register(request)
                result.fold(
                    onSuccess = { response ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = response.message
                        )
                        // Navigate to parent home as the parent is the master user
                        _navigationEvent.send(RegistrationNavigationEvent.NavigateToParentHome)
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = error.message ?: "注册失败"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "注册失败：${e.message}"
                )
            }
        }
    }
    
    fun navigateToLogin() {
        viewModelScope.launch {
            _navigationEvent.send(RegistrationNavigationEvent.NavigateToLogin)
        }
    }
    
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
    
    // Validation methods
    private fun validateStudentUsername(username: String): String? {
        return when {
            username.isBlank() -> "请输入学生姓名"
            username.length > 50 -> "姓名不能超过50个字符"
            else -> null
        }
    }
    
    private fun validateStudentTelephone(telephone: String): String? {
        return when {
            telephone.isBlank() -> "请输入学生手机号"
            telephone.length > 20 -> "手机号不能超过20个字符"
            !telephone.matches(Regex("^1[3-9]\\d{9}$")) -> "请输入有效的手机号"
            else -> null
        }
    }
    
    private fun validateStudentAge(age: String): String? {
        return when {
            age.isBlank() -> "请输入学生年龄"
            age.toIntOrNull() == null -> "请输入有效的年龄"
            age.toInt() !in 0..150 -> "年龄必须在0-150之间"
            else -> null
        }
    }
    
    private fun validateStudentPassword(password: String): String? {
        return when {
            password.isBlank() -> "请输入学生密码"
            password.length < 6 -> "密码至少6个字符"
            else -> null
        }
    }
    
    private fun validateParentUsername(username: String): String? {
        return when {
            username.isBlank() -> "请输入家长姓名"
            username.length > 50 -> "姓名不能超过50个字符"
            else -> null
        }
    }
    
    private fun validateParentTelephone(telephone: String): String? {
        return when {
            telephone.isBlank() -> "请输入家长手机号"
            telephone.length > 20 -> "手机号不能超过20个字符"
            !telephone.matches(Regex("^1[3-9]\\d{9}$")) -> "请输入有效的手机号"
            else -> null
        }
    }
    
    private fun validateParentPassword(password: String): String? {
        return when {
            password.isBlank() -> "请输入家长密码"
            password.length < 6 -> "密码至少6个字符"
            else -> null
        }
    }
    
    private fun validateForm(state: RegistrationUiState): Boolean {
        return validateStudentUsername(state.studentUsername) == null &&
                validateStudentTelephone(state.studentTelephone) == null &&
                validateStudentAge(state.studentAge) == null &&
                state.studentSex != null &&
                state.studentGrade != null &&
                validateStudentPassword(state.studentPassword) == null &&
                validateParentUsername(state.parentUsername) == null &&
                validateParentTelephone(state.parentTelephone) == null &&
                state.parentType != null &&
                validateParentPassword(state.parentPassword) == null
    }
}
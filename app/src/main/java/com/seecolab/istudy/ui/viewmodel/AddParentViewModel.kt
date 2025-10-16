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

data class AddParentUiState(
    val username: String = "",
    val telephone: String = "",
    val userType: UserTypeEnum? = null,
    val password: String = "",
    
    // Dropdown states
    val userTypeDropdownExpanded: Boolean = false,
    
    // Validation errors
    val usernameError: String? = null,
    val telephoneError: String? = null,
    val userTypeError: String? = null,
    val passwordError: String? = null,
    
    // UI states
    val isLoading: Boolean = false,
    val message: String? = null,
    val isFormValid: Boolean = false
)

sealed class AddParentNavigationEvent {
    object NavigateBack : AddParentNavigationEvent()
}

@HiltViewModel
class AddParentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddParentUiState())
    val uiState: StateFlow<AddParentUiState> = _uiState.asStateFlow()
    
    private val _navigationEvent = Channel<AddParentNavigationEvent>()
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
    
    fun updateUserType(userType: UserTypeEnum) {
        _uiState.value = _uiState.value.copy(
            userType = userType,
            userTypeError = null
        )
    }
    
    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = validatePassword(password)
        )
    }
    
    // Dropdown state updates
    fun updateUserTypeDropdownExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(userTypeDropdownExpanded = expanded)
    }
    
    // Actions
    fun addParent() {
        val state = _uiState.value
        if (!validateForm(state)) {
            _uiState.value = state.copy(message = "请检查输入信息")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, message = null)
            
            try {
                val request = ParentCreate(
                    username = state.username,
                    telephone = state.telephone,
                    parent_type = state.userType!!.value, // Use .value to get integer
                    password = state.password
                )
                
                val result = userRepository.createParent(request)
                result.fold(
                    onSuccess = { response ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = "家长添加成功！"
                        )
                        // Navigate back after success
                        _navigationEvent.send(AddParentNavigationEvent.NavigateBack)
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = error.message ?: "添加家长失败"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "添加家长失败：${e.message}"
                )
            }
        }
    }
    
    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.send(AddParentNavigationEvent.NavigateBack)
        }
    }
    
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
    
    // Validation methods
    private fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> "请输入家长姓名"
            username.length > 50 -> "姓名不能超过50个字符"
            else -> null
        }
    }
    
    private fun validateTelephone(telephone: String): String? {
        return when {
            telephone.isBlank() -> "请输入家长手机号"
            telephone.length > 20 -> "手机号不能超过20个字符"
            !telephone.matches(Regex("^1[3-9]\\d{9}$")) -> "请输入有效的手机号"
            else -> null
        }
    }
    
    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "请输入家长密码"
            password.length < 6 -> "密码至少6个字符"
            else -> null
        }
    }
    
    private fun validateForm(state: AddParentUiState): Boolean {
        return validateUsername(state.username) == null &&
                validateTelephone(state.telephone) == null &&
                state.userType != null &&
                validatePassword(state.password) == null
    }
}
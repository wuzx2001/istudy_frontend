package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignupUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val verificationCode: String = "",
    val selectedRole: UserRole = UserRole.STUDENT,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val verificationError: String? = null,
    val isLoading: Boolean = false,
    val isCodeSent: Boolean = false,
    val countdown: Int = 0,
    val message: String? = null
)

sealed class SignupNavigationEvent {
    object NavigateToStudentHome : SignupNavigationEvent()
    object NavigateToParentHome : SignupNavigationEvent()
    object NavigateToLogin : SignupNavigationEvent()
}

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignupNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private var countdownJob: Job? = null

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(
            phoneNumber = phoneNumber,
            phoneError = null
        )
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null
        )
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = null
        )
    }

    fun updateVerificationCode(code: String) {
        _uiState.value = _uiState.value.copy(
            verificationCode = code,
            verificationError = null
        )
    }

    fun updateRole(role: UserRole) {
        _uiState.value = _uiState.value.copy(selectedRole = role)
    }

    fun sendVerificationCode() {
        val currentState = _uiState.value
        
        if (!validatePhoneNumber(currentState.phoneNumber)) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Since there's no actual verification function, simulate sending
                val result = authRepository.sendVerificationCode(currentState.phoneNumber)
                
                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isCodeSent = true,
                            message = "验证码发送成功"
                        )
                        startCountdown()
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            verificationError = error.message ?: "发送验证码失败"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    verificationError = "网络错误，请重试"
                )
            }
        }
    }

    fun signup() {
        val currentState = _uiState.value
        
        if (!validateSignupForm(currentState)) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val signupRequest = SignupRequest(
                    phoneNumber = currentState.phoneNumber,
                    password = currentState.password,
                    role = currentState.selectedRole,
                    verificationCode = currentState.verificationCode
                )
                
                val result = authRepository.legacySignup(signupRequest)
                
                result.fold(
                    onSuccess = { response ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = response.message
                        )
                        
                        if (response.success) {
                            // Navigate based on user role
                            when (currentState.selectedRole) {
                                UserRole.STUDENT -> _navigationEvent.emit(SignupNavigationEvent.NavigateToStudentHome)
                                UserRole.PARENT -> _navigationEvent.emit(SignupNavigationEvent.NavigateToParentHome)
                            }
                        }
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = error.message ?: "注册失败，请重试"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "网络错误，请重试"
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    fun navigateToLogin() {
        viewModelScope.launch {
            _navigationEvent.emit(SignupNavigationEvent.NavigateToLogin)
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return when {
            phoneNumber.isBlank() -> {
                _uiState.value = _uiState.value.copy(phoneError = "请输入手机号")
                false
            }
            phoneNumber.length != 11 -> {
                _uiState.value = _uiState.value.copy(phoneError = "请输入正确的手机号")
                false
            }
            else -> true
        }
    }

    private fun validateSignupForm(state: SignupUiState): Boolean {
        var isValid = true

        // Validate phone number
        if (!validatePhoneNumber(state.phoneNumber)) {
            isValid = false
        }

        // Validate password
        if (state.password.isBlank()) {
            _uiState.value = _uiState.value.copy(passwordError = "请输入密码")
            isValid = false
        } else if (state.password.length < 6) {
            _uiState.value = _uiState.value.copy(passwordError = "密码至少6位")
            isValid = false
        }

        // Validate confirm password
        if (state.confirmPassword != state.password) {
            _uiState.value = _uiState.value.copy(confirmPasswordError = "两次密码输入不一致")
            isValid = false
        }

        // Accept any verification code since there's no actual verification
        if (state.verificationCode.isBlank()) {
            _uiState.value = _uiState.value.copy(verificationError = "请输入验证码")
            isValid = false
        }
        // Removed length validation - accept any code

        return isValid
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            for (i in 60 downTo 1) {
                _uiState.value = _uiState.value.copy(countdown = i)
                delay(1000)
            }
            _uiState.value = _uiState.value.copy(countdown = 0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
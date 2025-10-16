package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    private val _navigationEvent = MutableSharedFlow<LoginNavigationEvent>()
    val navigationEvent: SharedFlow<LoginNavigationEvent> = _navigationEvent.asSharedFlow()
    
    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber, phoneError = null) }
    }
    
    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }
    
    fun updateVerificationCode(code: String) {
        _uiState.update { it.copy(verificationCode = code, verificationError = null) }
    }
    
    fun switchLoginType(type: LoginType) {
        _uiState.update { 
            it.copy(
                loginType = type,
                passwordError = null,
                verificationError = null,
                isCodeSent = false,
                countdown = 0
            )
        }
    }
    
    fun sendVerificationCode() {
        val currentState = _uiState.value
        
        // Accept any phone number format during development
        if (currentState.phoneNumber.isBlank()) {
            _uiState.update { it.copy(phoneError = "请输入手机号") }
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Following memory guidance: sendVerificationCode should accept only phone number and return Result<Unit>
            authRepository.sendVerificationCode(currentState.phoneNumber)
                .onSuccess {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isCodeSent = true,
                            countdown = 60,
                            message = "验证码发送成功"
                        )
                    }
                    startCountdown()
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            verificationError = error.message ?: "发送验证码失败"
                        )
                    }
                }
        }
    }
    
    fun login() {
        val currentState = _uiState.value
        
        if (!validateInput(currentState)) {
            return
        }
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            if (currentState.loginType == LoginType.PASSWORD) {
                // Use new backend API for password login
                // Backend will determine user type automatically
                val loginRequest = LoginRequest(
                    username = currentState.phoneNumber,
                    password = currentState.password
                    // user_type removed - backend will determine this automatically
                )
                
                authRepository.login(loginRequest)
                    .onSuccess { response ->
                        _uiState.update { it.copy(isLoading = false) }
                        
                        // Navigate based on user_type from response
                        _navigationEvent.emit(
                            when (response.user_type) {
                                "student" -> LoginNavigationEvent.NavigateToStudentHome
                                "parent" -> LoginNavigationEvent.NavigateToParentHome
                                else -> LoginNavigationEvent.NavigateToParentHome // Default
                            }
                        )
                    }
                    .onFailure { error ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                message = when {
                                    error.message?.contains("401") == true -> "用户名或密码错误"
                                    error.message?.contains("Invalid credentials") == true -> "用户名或密码错误"
                                    error.message?.contains("用户名或密码错误") == true -> "用户名或密码错误"
                                    error.message?.contains("ConnectException") == true -> "无法连接到服务器，请检查网络连接"
                                    error.message?.contains("SocketTimeoutException") == true -> "请求超时，请稍后重试"
                                    error.message?.contains("UnknownHostException") == true -> "无法解析服务器地址，请检查网络设置"
                                    error.message?.contains("network") == true -> "网络错误，请检查网络连接"
                                    error.message?.contains("timeout") == true -> "请求超时，请稍后重试"
                                    error.message?.contains("HTTP 500") == true -> "服务器内部错误，请联系管理员"
                                    error.message?.contains("HTTP 404") == true -> "API接口不存在，请检查服务器配置"
                                    else -> "登录失败：${error.message ?: "未知错误"}"
                                }
                            )
                        }
                    }
            } else {
                // Use legacy signup for verification code login  
                val signupRequest = SignupRequest(
                    phoneNumber = currentState.phoneNumber,
                    password = "", // Not needed for verification code login
                    role = UserRole.PARENT, // Legacy system default - will be updated post-login
                    verificationCode = currentState.verificationCode
                )
                
                authRepository.legacySignup(signupRequest)
                    .onSuccess { response ->
                        _uiState.update { it.copy(isLoading = false) }
                        
                        if (response.success) {
                            // Default to parent home for verification code login
                            _navigationEvent.emit(LoginNavigationEvent.NavigateToParentHome)
                        } else {
                            _uiState.update { it.copy(message = response.message) }
                        }
                    }
                    .onFailure { error ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                message = error.message ?: "登录失败"
                            )
                        }
                    }
            }
        }
    }
    
    private fun validateInput(state: LoginUiState): Boolean {
        var isValid = true
        
        // Basic validation - just check if fields are not empty
        if (state.phoneNumber.isBlank()) {
            _uiState.update { it.copy(phoneError = "请输入用户名") }
            isValid = false
        }
        
        when (state.loginType) {
            LoginType.PASSWORD -> {
                if (state.password.isBlank()) {
                    _uiState.update { it.copy(passwordError = "请输入密码") }
                    isValid = false
                }
            }
            LoginType.VERIFICATION_CODE -> {
                if (state.verificationCode.isBlank()) {
                    _uiState.update { it.copy(verificationError = "请输入验证码") }
                    isValid = false
                }
            }
        }
        
        return isValid
    }
    
    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex("^1[3-9]\\d{9}$"))
    }
    
    private fun startCountdown() {
        viewModelScope.launch {
            while (_uiState.value.countdown > 0) {
                kotlinx.coroutines.delay(1000)
                _uiState.update { it.copy(countdown = it.countdown - 1) }
            }
        }
    }
    
    fun navigateToRegistration() {
        viewModelScope.launch {
            _navigationEvent.emit(LoginNavigationEvent.NavigateToRegistration)
        }
    }
    
    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
}

data class LoginUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val verificationCode: String = "",
    val loginType: LoginType = LoginType.PASSWORD,
    val isLoading: Boolean = false,
    val isCodeSent: Boolean = false,
    val countdown: Int = 0,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val verificationError: String? = null,
    val message: String? = null
)

sealed class LoginNavigationEvent {
    object NavigateToStudentHome : LoginNavigationEvent()
    object NavigateToParentHome : LoginNavigationEvent()
    object NavigateToRegistration : LoginNavigationEvent()
}
package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.api.AuthService
import com.seecolab.istudy.data.api.LegacyAuthService
import com.seecolab.istudy.data.local.dao.UserDao
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val legacyAuthService: LegacyAuthService,
    private val userDao: UserDao,
    private val tokenManager: TokenManager
) {
    
    fun getCurrentUserFlow(): Flow<User?> = userDao.getCurrentUserFlow()
    
    suspend fun getCurrentUser(): User? = userDao.getCurrentUser()
    
    // New backend API methods
    suspend fun register(request: RegistrationRequest): Result<RegistrationResponse> {
        return try {
            val response = authService.register(request)
            if (response.isSuccessful) {
                response.body()?.let { registrationResponse ->
                    // RegistrationResponse doesn't include access_token in the new format
                    // Save parent user to local database for login tracking
                    val user = User(
                        phoneNumber = registrationResponse.parent.telephone,
                        role = UserRole.PARENT,
                        isLoggedIn = true
                    )
                    userDao.logoutAllUsers()
                    userDao.insertUser(user)
                    userDao.loginUser(registrationResponse.parent.telephone)
                    
                    Result.success(registrationResponse)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("注册失败: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = authService.login(request)
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    // Store JWT token
                    tokenManager.saveToken(
                        accessToken = loginResponse.access_token,
                        tokenType = loginResponse.token_type,
                        expiresIn = loginResponse.expires_in,
                        userId = loginResponse.user_id,
                        username = loginResponse.username,
                        userType = loginResponse.user_type
                    )
                    
                    // Save user to local database for navigation purposes
                    val user = User(
                        phoneNumber = loginResponse.username, // Assuming username is phone number
                        role = if (loginResponse.user_type == "student") UserRole.STUDENT else UserRole.PARENT,
                        isLoggedIn = true
                    )
                    userDao.logoutAllUsers()
                    userDao.insertUser(user)
                    userDao.loginUser(loginResponse.username)
                    
                    Result.success(loginResponse)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("登录失败: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout(): Result<String> {
        return try {
            val userId = tokenManager.getUserId()
            val userType = tokenManager.getUserType()
            
            if (userId != null && userType != null) {
                val userTypeInt = when (userType) {
                    "student" -> 0
                    "parent" -> 1
                    else -> 1 // Default to parent
                }
                
                val logoutRequest = LogoutRequest(
                    user_id = userId,
                    user_type = userTypeInt
                )
                
                try {
                    val response = authService.logout(logoutRequest)
                    // Clear token and local data regardless of API response
                    tokenManager.clearToken()
                    userDao.logoutAllUsers()
                    
                    if (response.isSuccessful) {
                        Result.success("Logout successful")
                    } else {
                        Result.success("Logout successful") // Always succeed locally
                    }
                } catch (e: Exception) {
                    // Still logout locally even if network fails
                    tokenManager.clearToken()
                    userDao.logoutAllUsers()
                    Result.success("Logout successful")
                }
            } else {
                // No valid session, just clear local data
                tokenManager.clearToken()
                userDao.logoutAllUsers()
                Result.success("No user logged in")
            }
        } catch (e: Exception) {
            // Still logout locally even if anything fails
            tokenManager.clearToken()
            userDao.logoutAllUsers()
            Result.success("Logout successful")
        }
    }
    
    suspend fun getCurrentUserFromApi(): Result<UserResponse> {
        return try {
            val response = authService.getCurrentUser()
            if (response.isSuccessful) {
                response.body()?.let { userResponse ->
                    Result.success(userResponse)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("获取用户信息失败: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun isUserLoggedIn(): Boolean {
        return tokenManager.isLoggedIn() && getCurrentUser() != null
    }
    
    suspend fun verifyToken(): Result<TokenVerifyResponse> {
        return try {
            // First check if we have a token locally
            if (!tokenManager.isLoggedIn()) {
                return Result.failure(Exception("No token available"))
            }
            
            val response = authService.verifyToken()
            if (response.isSuccessful) {
                response.body()?.let { verifyResponse ->
                    // If token is invalid or expired, clear local data
                    if (!verifyResponse.valid || verifyResponse.expired) {
                        tokenManager.clearToken()
                        userDao.logoutAllUsers()
                    }
                    Result.success(verifyResponse)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                // If request fails, assume token is invalid and clear local data
                tokenManager.clearToken()
                userDao.logoutAllUsers()
                Result.failure(Exception("Token verification failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            // If network error or other exception, clear local data for security
            tokenManager.clearToken()
            userDao.logoutAllUsers()
            Result.failure(e)
        }
    }
    
    // Legacy methods for backward compatibility - following memory guidance
    suspend fun sendVerificationCode(phoneNumber: String): Result<Unit> {
        return try {
            val request = VerificationCodeRequest(phoneNumber = phoneNumber)
            val response = legacyAuthService.sendVerificationCode(request)
            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    if (apiResponse.success) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception(apiResponse.message ?: "发送验证码失败"))
                    }
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("发送验证码失败: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun legacySignup(request: SignupRequest): Result<SignupResponse> {
        return try {
            val response = legacyAuthService.legacySignup(request)
            if (response.isSuccessful) {
                response.body()?.let { signupResponse ->
                    if (signupResponse.success && signupResponse.user != null) {
                        // Save user to local database
                        userDao.logoutAllUsers() // Logout any existing user  
                        userDao.insertUser(signupResponse.user)
                        userDao.loginUser(signupResponse.user.phoneNumber)
                    }
                    Result.success(signupResponse)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("注册失败: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
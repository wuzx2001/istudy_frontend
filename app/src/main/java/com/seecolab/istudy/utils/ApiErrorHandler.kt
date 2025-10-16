package com.seecolab.istudy.utils

import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.local.dao.UserDao
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centralized API error handler for authentication errors
 * Automatically clears local data and triggers login redirect when authentication fails
 */
@Singleton
class ApiErrorHandler @Inject constructor(
    private val tokenManager: TokenManager,
    private val userDao: UserDao,
    private val authenticationGuard: AuthenticationGuard
) {
    
    /**
     * Handles API response and checks for authentication errors
     * @param response The Retrofit response to check
     * @return true if the response indicates authentication failure, false otherwise
     */
    suspend fun <T> handleAuthenticationError(response: Response<T>): Boolean {
        val isAuthError = when (response.code()) {
            401, 403 -> true // Unauthorized or Forbidden
            else -> false
        }
        
        if (isAuthError) {
            // Clear local authentication data
            clearAuthenticationData()
            
            // Emit authentication failed event to trigger login redirect
            authenticationGuard.emitAuthenticationFailed(
                "Authentication failed: ${response.message()}"
            )
        }
        
        return isAuthError
    }
    
    /**
     * Handles exceptions that might indicate authentication failures
     * @param exception The exception to check
     * @return true if the exception indicates authentication failure, false otherwise
     */
    suspend fun handleAuthenticationException(exception: Exception): Boolean {
        val isAuthError = when {
            exception.message?.contains("401") == true -> true
            exception.message?.contains("403") == true -> true
            exception.message?.contains("Unauthorized") == true -> true
            exception.message?.contains("Forbidden") == true -> true
            exception.message?.contains("Authentication") == true -> true
            else -> false
        }
        
        if (isAuthError) {
            // Clear local authentication data
            clearAuthenticationData()
            
            // Emit authentication failed event to trigger login redirect
            authenticationGuard.emitAuthenticationFailed(
                "Authentication error: ${exception.message}"
            )
        }
        
        return isAuthError
    }
    
    /**
     * Wraps a repository API call to automatically handle authentication errors
     * @param apiCall The API call to execute
     * @return Result with proper authentication error handling
     */
    suspend fun <T> executeWithAuthHandling(
        apiCall: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = apiCall()
            
            // Check for authentication errors first
            if (handleAuthenticationError(response)) {
                Result.failure(Exception("Authentication failed: ${response.message()}"))
            } else if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("API call failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            // Check if the exception indicates authentication failure
            if (handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    /**
     * Clears all local authentication data
     */
    private suspend fun clearAuthenticationData() {
        try {
            tokenManager.clearToken()
            userDao.logoutAllUsers()
        } catch (e: Exception) {
            // Log error but don't throw - clearing data should be fire-and-forget
            android.util.Log.w("ApiErrorHandler", "Error clearing auth data: ${e.message}")
        }
    }
}
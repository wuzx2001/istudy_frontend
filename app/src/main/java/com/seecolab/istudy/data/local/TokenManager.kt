package com.seecolab.istudy.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "auth_preferences", 
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val TOKEN_TYPE_KEY = "token_type"
        private const val EXPIRES_IN_KEY = "expires_in"
        private const val TOKEN_SAVED_TIME_KEY = "token_saved_time"
        private const val USER_ID_KEY = "user_id"
        private const val USERNAME_KEY = "username"
        private const val USER_TYPE_KEY = "user_type"
    }
    
    fun saveToken(
        accessToken: String,
        tokenType: String = "bearer",
        expiresIn: Int,
        userId: String,
        username: String,
        userType: String
    ) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(TOKEN_TYPE_KEY, tokenType)
            putInt(EXPIRES_IN_KEY, expiresIn)
            putLong(TOKEN_SAVED_TIME_KEY, System.currentTimeMillis())
            putString(USER_ID_KEY, userId)
            putString(USERNAME_KEY, username)
            putString(USER_TYPE_KEY, userType)
            apply()
        }
    }
    
    fun getAccessToken(): String? {
        val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        return if (isTokenValid()) token else null
    }
    
    fun getTokenType(): String {
        return sharedPreferences.getString(TOKEN_TYPE_KEY, "bearer") ?: "bearer"
    }
    
    fun getBearerToken(): String? {
        return try {
            val token = getAccessToken()
            if (token != null) {
                val tokenType = getTokenType()
                "${tokenType.replaceFirstChar { it.uppercaseChar() }} $token"
            } else {
                null
            }
        } catch (e: Exception) {
            // If any error occurs, return null to avoid authentication issues
            null
        }
    }
    
    fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID_KEY, null)
    }
    
    fun getUsername(): String? {
        return sharedPreferences.getString(USERNAME_KEY, null)
    }
    
    fun getUserType(): String? {
        return sharedPreferences.getString(USER_TYPE_KEY, null)
    }
    
    fun isTokenValid(): Boolean {
        val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        if (token.isNullOrEmpty()) return false
        
        try {
            val expiresIn = sharedPreferences.getInt(EXPIRES_IN_KEY, 0)
            val savedTime = sharedPreferences.getLong(TOKEN_SAVED_TIME_KEY, 0)
            val currentTime = System.currentTimeMillis()
            
            // If expiresIn is 0 or savedTime is 0, consider token invalid
            if (expiresIn <= 0 || savedTime <= 0) return false
            
            // Check if token has expired (with 5 minute buffer)
            val expirationTime = savedTime + (expiresIn * 1000) - (5 * 60 * 1000)
            return currentTime < expirationTime
        } catch (e: Exception) {
            // If any error occurs in token validation, consider token invalid
            return false
        }
    }
    
    fun isLoggedIn(): Boolean {
        return isTokenValid() && !getUserId().isNullOrEmpty()
    }
    
    fun clearToken() {
        sharedPreferences.edit().apply {
            remove(ACCESS_TOKEN_KEY)
            remove(TOKEN_TYPE_KEY)
            remove(EXPIRES_IN_KEY)
            remove(TOKEN_SAVED_TIME_KEY)
            remove(USER_ID_KEY)
            remove(USERNAME_KEY)
            remove(USER_TYPE_KEY)
            apply()
        }
    }
    
    fun getTokenExpirationTime(): Long {
        val expiresIn = sharedPreferences.getInt(EXPIRES_IN_KEY, 0)
        val savedTime = sharedPreferences.getLong(TOKEN_SAVED_TIME_KEY, 0)
        return savedTime + (expiresIn * 1000)
    }
    
    fun getRemainingTokenTime(): Long {
        val expirationTime = getTokenExpirationTime()
        val currentTime = System.currentTimeMillis()
        return maxOf(0, expirationTime - currentTime)
    }
}
package com.seecolab.istudy.data.api

import com.seecolab.istudy.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.toString()
        
        // Skip authentication for auth endpoints
        val isAuthEndpoint = url.contains("/auth/login") || 
                            url.contains("/auth/register") ||
                            url.contains("/auth/send-verification-code") ||
                            url.contains("/auth/legacy-signup")
        
        val authenticatedRequest = run {
            val requestBuilder = originalRequest.newBuilder()
                .addHeader("User-Agent", "iStudy-Android-App")
            
            // Only add Content-Type for non-multipart requests
            val contentType = originalRequest.header("Content-Type")
            if (contentType == null && !url.contains("/files/")) {
                requestBuilder.addHeader("Content-Type", "application/json")
                requestBuilder.addHeader("Accept", "application/json")
            }
            
            // Add JWT token for authenticated endpoints only when we have a token
            if (!isAuthEndpoint) {
                val bearerToken = tokenManager.getBearerToken()
                if (bearerToken != null) {
                    requestBuilder.addHeader("Authorization", bearerToken)
                }
            }
            
            requestBuilder.build()
        }
        
        return chain.proceed(authenticatedRequest)
    }
}
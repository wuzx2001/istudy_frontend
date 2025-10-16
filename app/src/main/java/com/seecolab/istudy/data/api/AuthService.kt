package com.seecolab.istudy.data.api

import com.seecolab.istudy.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body request: RegistrationRequest): Response<RegistrationResponse>
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @POST("auth/logout")
    suspend fun logout(@Body request: LogoutRequest): Response<ApiResponse<String>>
    
    @GET("auth/me")
    suspend fun getCurrentUser(): Response<UserResponse>
    
    @POST("auth/verify-token")
    suspend fun verifyToken(): Response<TokenVerifyResponse>
}

// Legacy interface for backward compatibility with SMS verification
interface LegacyAuthService {
    @POST("auth/send-verification-code")
    suspend fun sendVerificationCode(@Body request: VerificationCodeRequest): Response<ApiResponse<String>>
    
    @POST("auth/legacy-signup")
    suspend fun legacySignup(@Body request: SignupRequest): Response<SignupResponse>
}
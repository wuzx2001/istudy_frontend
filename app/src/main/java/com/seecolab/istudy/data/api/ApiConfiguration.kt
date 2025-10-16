package com.seecolab.istudy.data.api

/**
 * API Configuration
 * Update the BASE_URL when backend is ready
 */
object ApiConfiguration {
    // TODO: Update this URL to your actual backend API
    const val BASE_URL = "http://61.169.177.226:8888/"  // Android emulator localhost mapping
    // For real device, use: "http://YOUR_BACKEND_IP:8000/"
    // For production: "https://your-backend-api.com/"
    
    // Authentication endpoints
    const val LOGIN_ENDPOINT = "auth/login"
    const val SEND_VERIFICATION_CODE_ENDPOINT = "auth/send-verification-code"
    const val LOGOUT_ENDPOINT = "auth/logout"
    
    // Connection timeout in seconds (extended to 5 minutes for file uploads)
    const val CONNECT_TIMEOUT = 300L
    const val READ_TIMEOUT = 300L
    const val WRITE_TIMEOUT = 300L
    
    // Development mode - set to false to use real API
    const val USE_MOCK_API = false  // Changed to false to use real API
}
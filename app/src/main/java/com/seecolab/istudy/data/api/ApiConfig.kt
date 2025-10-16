package com.seecolab.istudy.data.api

/**
 * API Configuration for Teacher Avatar AI Service
 * Replace these values with your actual credentials from the AI service
 */
object ApiConfig {
    // TODO: Replace with your actual API base URL from the GitHub repo
    const val BASE_URL = "https://test.quants.top"  // e.g., "https://api.volcengine.com/"
    
    // TODO: Replace with your actual API key/token
    const val API_KEY = "YOUR_API_KEY_HERE"
    
    // TODO: Replace with your access token if required
    const val ACCESS_TOKEN = "YOUR_ACCESS_TOKEN_HERE"
    
    // TODO: Replace with your secret key if required
    const val SECRET_KEY = "YOUR_SECRET_KEY_HERE"
    
    // API endpoints
    const val HOMEWORK_ANALYZE_ENDPOINT = "api/homework/analyze"
    const val HOMEWORK_RESULT_ENDPOINT = "api/homework/result"
    
    // Request headers
    const val HEADER_API_KEY = "X-API-Key"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_CONTENT_TYPE = "Content-Type"
}
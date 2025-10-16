# API Integration Guide

## Current Status
The app is currently configured to accept all login data without validation, making it ready for development and testing with any credentials.

## Development Mode Settings
- ✅ **Accept all phone numbers** (no format validation)
- ✅ **Accept any password** (no length requirement)
- ✅ **Accept any verification code** (no specific format required)
- ✅ **Mock API responses** always return success
- ✅ **Network delay simulation** (1 second for login, 0.5 seconds for SMS)

## Switching to Real Backend API

### Step 1: Update API Configuration
Edit the file: `app/src/main/java/com/seecolab/istudy/data/api/ApiConfiguration.kt`

```kotlin
object ApiConfiguration {
    // 1. Update this URL to your backend API
    const val BASE_URL = "https://your-actual-backend-api.com/api/"
    
    // 2. Set this to false to use real API
    const val USE_MOCK_API = false
    
    // 3. Update endpoints if needed
    const val LOGIN_ENDPOINT = "auth/login"
    const val SEND_VERIFICATION_CODE_ENDPOINT = "auth/send-verification-code"
    const val LOGOUT_ENDPOINT = "auth/logout"
}
```

### Step 2: API Contract
Your backend should implement these endpoints:

#### POST /auth/login
**Request:**
```json
{
    "phoneNumber": "string",
    "password": "string (optional)",
    "verificationCode": "string (optional)", 
    "role": "STUDENT" | "PARENT",
    "loginType": "PASSWORD" | "VERIFICATION_CODE"
}
```

**Response:**
```json
{
    "success": true,
    "message": "Login successful",
    "user": {
        "phoneNumber": "string",
        "role": "STUDENT" | "PARENT",
        "isLoggedIn": true,
        "lastLoginTime": 1699999999999,
        "createdAt": 1699999999999
    },
    "token": "jwt_token_here"
}
```

#### POST /auth/send-verification-code
**Request:**
```json
{
    "phoneNumber": "string"
}
```

**Response:**
```json
{
    "success": true,
    "message": "Verification code sent successfully",
    "data": "123456"
}
```

#### POST /auth/logout
**Request:** Empty body or include user token in header

**Response:**
```json
{
    "success": true,
    "message": "Logout successful"
}
```

### Step 3: Testing the Integration

1. **Update the configuration** (Step 1)
2. **Build and run the app**
3. **Test login with real credentials**
4. **Check network logs** for API calls
5. **Verify authentication flow**

### Step 4: Error Handling
The app already includes comprehensive error handling for:
- Network connection issues
- Invalid credentials
- Server errors
- Timeout issues

### Step 5: Authentication Headers
If your API requires authentication headers (like JWT tokens), you can update the `AuthenticationInterceptor` class:

```kotlin
// File: app/src/main/java/com/seecolab/istudy/data/api/AuthenticationInterceptor.kt
class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer YOUR_TOKEN_HERE")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}
```

## Current Development Features

### Login Testing (Development Mode)
- **Any phone number:** Works (e.g., "123", "test@email.com", "1234567890")
- **Any password:** Works (e.g., "123", "password", "a")
- **Any verification code:** Works (e.g., "123", "000000", "abcd")
- **Both roles:** Student and Parent both work
- **Network simulation:** 1-second delay for realistic testing

### What Happens During Login
1. User enters credentials
2. App shows loading state
3. Mock API simulates network call (1s delay)
4. Always returns success response
5. User data saved to local database
6. Navigation to appropriate home screen based on role

### Data Flow
```
LoginScreen → LoginViewModel → AuthRepository → AuthService (Mock) → Success Response → Local Database → Navigation
```

## Ready for Production
When you provide the backend API URL, simply:
1. Update `BASE_URL` in `ApiConfiguration.kt`
2. Set `USE_MOCK_API = false`
3. Build and deploy

The app will automatically switch to using the real API endpoints while maintaining all the existing UI, validation, and error handling logic.

## Notes
- All login data is currently accepted for maximum development flexibility
- Real validation can be added back when needed
- The app architecture supports easy switching between mock and real APIs
- Authentication state is properly managed with Room database persistence
- Role-based navigation is fully implemented
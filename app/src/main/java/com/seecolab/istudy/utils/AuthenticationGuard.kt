package com.seecolab.istudy.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationGuard @Inject constructor(
    private val authRepository: AuthRepository
) {
    
    private val _authenticationEvents = MutableSharedFlow<AuthenticationEvent>()
    val authenticationEvents: SharedFlow<AuthenticationEvent> = _authenticationEvents.asSharedFlow()
    
    /**
     * Verifies the current user's token and triggers logout if invalid
     * @return true if authenticated, false if not authenticated
     */
    suspend fun verifyAuthentication(): Boolean {
        return try {
            val result = authRepository.verifyToken()
            result.fold(
                onSuccess = { verifyResponse ->
                    if (verifyResponse.valid && !verifyResponse.expired) {
                        true
                    } else {
                        // Token is invalid or expired
                        _authenticationEvents.emit(
                            AuthenticationEvent.TokenExpired(verifyResponse.message)
                        )
                        false
                    }
                },
                onFailure = { exception ->
                    // Token verification failed
                    _authenticationEvents.emit(
                        AuthenticationEvent.AuthenticationFailed(
                            exception.message ?: "Authentication verification failed"
                        )
                    )
                    false
                }
            )
        } catch (e: Exception) {
            _authenticationEvents.emit(
                AuthenticationEvent.AuthenticationFailed(
                    e.message ?: "Authentication check failed"
                )
            )
            false
        }
    }
    
    /**
     * Quick local authentication check without API call
     */
    suspend fun isLocallyAuthenticated(): Boolean {
        return authRepository.isUserLoggedIn()
    }
    
    /**
     * Manually emit authentication failed event (for use by ApiErrorHandler)
     */
    suspend fun emitAuthenticationFailed(message: String) {
        _authenticationEvents.emit(
            AuthenticationEvent.AuthenticationFailed(message)
        )
    }
}

sealed class AuthenticationEvent {
    data class TokenExpired(val message: String) : AuthenticationEvent()
    data class AuthenticationFailed(val message: String) : AuthenticationEvent()
}

/**
 * Base ViewModel that provides authentication checking functionality
 */
abstract class AuthenticatedViewModel(
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _authenticationEvents = MutableSharedFlow<AuthenticationEvent>()
    val authenticationEvents: SharedFlow<AuthenticationEvent> = _authenticationEvents.asSharedFlow()
    
    /**
     * Call this method when the screen/view is entered to verify authentication
     */
    protected fun checkAuthentication() {
        viewModelScope.launch {
            if (!authenticationGuard.verifyAuthentication()) {
                // Authentication failed, collect events and re-emit them
                authenticationGuard.authenticationEvents.collect { event ->
                    _authenticationEvents.emit(event)
                }
            }
        }
    }
    
    /**
     * Override this method to perform additional authentication logic
     */
    protected open fun onAuthenticationRequired() {
        checkAuthentication()
    }
}
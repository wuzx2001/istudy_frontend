package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.repository.AuthRepository
import com.seecolab.istudy.utils.AuthenticationEvent
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthNavigationViewModel @Inject constructor(
    val authRepository: AuthRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _navigationEvents = MutableSharedFlow<AuthNavigationEvent>()
    val navigationEvents: SharedFlow<AuthNavigationEvent> = _navigationEvents.asSharedFlow()
    
    init {
        // Listen for authentication events and handle them
        viewModelScope.launch {
            authenticationGuard.authenticationEvents.collect { event ->
                when (event) {
                    is AuthenticationEvent.TokenExpired,
                    is AuthenticationEvent.AuthenticationFailed -> {
                        // Clear any local state and redirect to login
                        _navigationEvents.emit(AuthNavigationEvent.NavigateToLogin(event.toString()))
                    }
                }
            }
        }
    }
    
    /**
     * Call this method when navigating to a protected screen
     */
    fun verifyAuthenticationForScreen() {
        viewModelScope.launch {
            if (!authenticationGuard.verifyAuthentication()) {
                // Authentication failed, event will be handled by the init block
                // which will emit NavigateToLogin
            }
        }
    }
    
    /**
     * Quick check for local authentication status
     */
    suspend fun isLocallyAuthenticated(): Boolean {
        return authenticationGuard.isLocallyAuthenticated()
    }
}

sealed class AuthNavigationEvent {
    data class NavigateToLogin(val reason: String) : AuthNavigationEvent()
}
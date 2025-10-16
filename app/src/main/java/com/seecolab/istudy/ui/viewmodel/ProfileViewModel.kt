package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.User
import com.seecolab.istudy.data.repository.AuthRepository
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    val currentUser: StateFlow<User?> = authRepository.getCurrentUserFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            authRepository.logout()
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    // Navigation will be handled automatically by the navigation component
                    // when the currentUser becomes null
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Logout failed"
                        )
                    }
                }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
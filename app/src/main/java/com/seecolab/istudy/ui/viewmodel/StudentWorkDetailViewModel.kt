package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.api.UploadService
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.utils.ApiErrorHandler
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentWorkDetailUiState(
    val isLoading: Boolean = false,
    val workDetail: StudentWorkResponse? = null,
    val errorMessage: String? = null,
    val showImageDialog: Boolean = false,
    val imageUrl: String? = null
)

@HiltViewModel
class StudentWorkDetailViewModel @Inject constructor(
    private val uploadService: UploadService,
    private val tokenManager: TokenManager,
    private val authenticationGuard: AuthenticationGuard,
    private val apiErrorHandler: ApiErrorHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentWorkDetailUiState())
    val uiState: StateFlow<StudentWorkDetailUiState> = _uiState.asStateFlow()

    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
    }

    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }

    fun loadWorkDetail(workId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

                val token = tokenManager.getBearerToken()
                if (token == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Authentication token not found"
                    )
                    return@launch
                }

                // Use ApiErrorHandler to handle authentication errors
                val result = apiErrorHandler.executeWithAuthHandling {
                    uploadService.getStudentWork(
                        workId = workId,
                        authorization = token
                    )
                }
                
                result.fold(
                    onSuccess = { workDetail ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            workDetail = workDetail,
                            errorMessage = null
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load work detail"
                        )
                    }
                )
            } catch (e: Exception) {
                // Check if exception indicates auth failure
                if (apiErrorHandler.handleAuthenticationException(e)) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Authentication failed"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    fun showImageDialog(imageUrl: String) {
        _uiState.value = _uiState.value.copy(
            showImageDialog = true,
            imageUrl = imageUrl
        )
    }
    
    fun hideImageDialog() {
        _uiState.value = _uiState.value.copy(
            showImageDialog = false,
            imageUrl = null
        )
    }
}
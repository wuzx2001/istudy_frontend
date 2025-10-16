package com.seecolab.istudy.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CorrectionResultUiState(
    val result: StudentWorkResponse? = null,
    val imageUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val showImageDialog: Boolean = false,
    val selectedImageIndex: Int = 0
)

@HiltViewModel
class CorrectionResultViewModel @Inject constructor(
    private val authenticationGuard: AuthenticationGuard
    // Removed uploadRepository dependency since we no longer use streaming
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CorrectionResultUiState())
    val uiState: StateFlow<CorrectionResultUiState> = _uiState.asStateFlow()
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }
    
    fun initializeData(
        result: StudentWorkResponse?,
        imageUris: List<Uri>
    ) {
        viewModelScope.launch {
            android.util.Log.d("CorrectionResultViewModel", "=== INITIALIZING RESULT DATA ===")
            android.util.Log.d("CorrectionResultViewModel", "Result: $result")
            android.util.Log.d("CorrectionResultViewModel", "Images: ${imageUris.size}")
            
            _uiState.value = _uiState.value.copy(
                result = result,
                imageUris = imageUris,
                isLoading = false
            )
            
            android.util.Log.d("CorrectionResultViewModel", "=== INITIALIZATION COMPLETE ===")
        }
    }
    
    fun showImageDialog(initialIndex: Int = 0) {
        _uiState.value = _uiState.value.copy(
            showImageDialog = true,
            selectedImageIndex = initialIndex
        )
    }
    
    fun hideImageDialog() {
        _uiState.value = _uiState.value.copy(
            showImageDialog = false
        )
    }
    
    fun selectImage(index: Int) {
        _uiState.value = _uiState.value.copy(
            selectedImageIndex = index
        )
    }
}
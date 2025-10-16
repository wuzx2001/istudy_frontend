package com.seecolab.istudy.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.data.repository.UploadRepository
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import javax.inject.Inject

data class UploadUiState(
    val isUploading: Boolean = false,
    val uploadResult: StudentWorkResponse? = null,
    val error: String? = null,
    val isComplete: Boolean = false,
    val isCancelling: Boolean = false
)

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UploadUiState())
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()
    
    private val _selectedImageUris = MutableStateFlow<List<Uri>>(emptyList())
    val selectedImageUris: StateFlow<List<Uri>> = _selectedImageUris.asStateFlow()
    
    // Keep the single image URI for backward compatibility
    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri.asStateFlow()
    
    private val _showCamera = MutableStateFlow(false)
    val showCamera: StateFlow<Boolean> = _showCamera.asStateFlow()
    
    private val _showGallery = MutableStateFlow(false)
    val showGallery: StateFlow<Boolean> = _showGallery.asStateFlow()
    
    // Job for upload operation to enable cancellation
    private var uploadJob: Job? = null
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }
    
    fun selectImageFromCamera() {
        _showCamera.value = true
    }
    
    fun selectImageFromGallery() {
        _showGallery.value = true
    }
    
    fun onImageCaptured(uri: Uri) {
        // Add to list of selected images
        val currentList = _selectedImageUris.value.toMutableList()
        currentList.add(uri)
        _selectedImageUris.value = currentList
        
        // Also update single image URI for backward compatibility
        _selectedImageUri.value = uri
        
        _showCamera.value = false
        _showGallery.value = false
    }
    
    fun onImagesSelected(uris: List<Uri>) {
        // Replace the entire list with new selection
        _selectedImageUris.value = uris
        
        // Update single image URI to the first image (for backward compatibility)
        _selectedImageUri.value = uris.firstOrNull()
        
        _showGallery.value = false
    }
    
    fun onImageSelected(uri: Uri) {
        // Add single image to the list
        val currentList = _selectedImageUris.value.toMutableList()
        if (!currentList.contains(uri)) {
            currentList.add(uri)
            _selectedImageUris.value = currentList
        }
        
        // Update single image URI
        _selectedImageUri.value = uri
        
        _showGallery.value = false
    }
    
    fun removeImage(uri: Uri) {
        val currentList = _selectedImageUris.value.toMutableList()
        currentList.remove(uri)
        _selectedImageUris.value = currentList
        
        // Update single image URI to the first remaining image or null
        _selectedImageUri.value = currentList.firstOrNull()
        
        // If no images left, reset the UI state
        if (currentList.isEmpty()) {
            _uiState.value = UploadUiState()
        }
    }
    
    fun clearAllImages() {
        _selectedImageUris.value = emptyList()
        _selectedImageUri.value = null
    }
    
    fun dismissCamera() {
        _showCamera.value = false
    }
    
    fun dismissGallery() {
        _showGallery.value = false
    }
    
    fun uploadImages(timeConsumingMinutes: Int) {
        val imageUris = _selectedImageUris.value
        if (imageUris.isEmpty()) {
            _uiState.update { it.copy(error = "Please select at least one image first") }
            return
        }
        
        // Cancel any existing upload
        uploadJob?.cancel()
        
        uploadJob = viewModelScope.launch {
            // Reset UI state for new upload
            _uiState.update { 
                it.copy(
                    isUploading = true, 
                    uploadResult = null, 
                    error = null, 
                    isComplete = false,
                    isCancelling = false
                ) 
            }
            
            try {
                // Make the upload request (传递 time_consuming)
                val result = uploadRepository.uploadPaperWork(imageUris, timeConsumingMinutes)
                
                result.fold(
                    onSuccess = { studentWorkResponse ->
                        _uiState.update { 
                            it.copy(
                                isUploading = false,
                                uploadResult = studentWorkResponse,
                                isComplete = true,
                                isCancelling = false
                            ) 
                        }
                    },
                    onFailure = { error ->
                        _uiState.update { 
                            it.copy(
                                isUploading = false,
                                error = "Upload failed: ${error.message}",
                                isComplete = true,
                                isCancelling = false
                            ) 
                        }
                    }
                )
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isUploading = false, 
                        error = "Upload failed: ${e.message}",
                        isComplete = true,
                        isCancelling = false
                    ) 
                }
            }
        }
    }
    
    fun uploadImage(timeConsumingMinutes: Int) {
        uploadImages(timeConsumingMinutes)
    }
    
    fun cancelUpload() {
        uploadJob?.let { job ->
            if (job.isActive) {
                _uiState.update { it.copy(isCancelling = true) }
                job.cancel()
                _uiState.update { 
                    it.copy(
                        isUploading = false,
                        isCancelling = false,
                        error = "Upload cancelled",
                        isComplete = true
                    )
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    fun reset() {
        uploadJob?.cancel()
        _selectedImageUri.value = null
        _selectedImageUris.value = emptyList()
        _uiState.value = UploadUiState()
    }
    
    fun clearImagesAfterSuccess() {
        // Clear images and reset upload state for new upload
        _selectedImageUris.value = emptyList()
        _selectedImageUri.value = null
        _uiState.value = UploadUiState() // Reset to initial state
    }
    
    fun resetAfterNavigation() {
        // Reset everything when navigating back from results
        uploadJob?.cancel()
        _selectedImageUris.value = emptyList()
        _selectedImageUri.value = null
        _uiState.value = UploadUiState()
        _showCamera.value = false
        _showGallery.value = false
    }
}
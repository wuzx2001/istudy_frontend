package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.data.repository.UploadRepository
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UploadResultUiState(
    val result: StudentWorkResponse? = null,
    val editableSubject: String = "",
    val editablePaperName: String = "",
    val hasChanges: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class UploadResultViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UploadResultUiState())
    val uiState: StateFlow<UploadResultUiState> = _uiState.asStateFlow()
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }
    
    fun initializeData(result: StudentWorkResponse) {
        _uiState.value = _uiState.value.copy(
            result = result,
            editableSubject = result.subject ?: "",
            editablePaperName = result.paper_name ?: "",
            hasChanges = false
        )
    }
    
    fun updateSubject(newSubject: String) {
        val currentResult = _uiState.value.result
        val hasChanges = newSubject != (currentResult?.subject ?: "") ||
                         _uiState.value.editablePaperName != (currentResult?.paper_name ?: "")
        
        _uiState.value = _uiState.value.copy(
            editableSubject = newSubject,
            hasChanges = hasChanges
        )
    }
    
    fun updatePaperName(newPaperName: String) {
        val currentResult = _uiState.value.result
        val hasChanges = newPaperName != (currentResult?.paper_name ?: "") ||
                         _uiState.value.editableSubject != (currentResult?.subject ?: "")
        
        _uiState.value = _uiState.value.copy(
            editablePaperName = newPaperName,
            hasChanges = hasChanges
        )
    }
    
    fun saveChanges() {
        val currentState = _uiState.value
        val result = currentState.result
        
        if (result == null || !currentState.hasChanges) {
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSaving = true,
                errorMessage = null
            )
            
            try {
                val updateResult = uploadRepository.updateAiCorrection(
                    workId = result.id,
                    subject = currentState.editableSubject,
                    paperName = currentState.editablePaperName
                )
                
                updateResult.fold(
                    onSuccess = { updatedResult ->
                        _uiState.value = _uiState.value.copy(
                            result = updatedResult,
                            isSaving = false,
                            hasChanges = false,
                            saveSuccess = true
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isSaving = false,
                            errorMessage = "保存失败: ${error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = "保存失败: ${e.message}"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    fun clearSaveSuccess() {
        _uiState.value = _uiState.value.copy(saveSuccess = false)
    }
}
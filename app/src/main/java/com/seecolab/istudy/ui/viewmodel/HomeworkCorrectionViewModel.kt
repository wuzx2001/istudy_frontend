package com.seecolab.istudy.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.api.HomeworkAnalysisResult
import com.seecolab.istudy.data.model.Grade
import com.seecolab.istudy.data.model.Subject
import com.seecolab.istudy.data.repository.HomeworkCorrectionRepository
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeworkCorrectionUiState(
    val isLoading: Boolean = false,
    val analysisResult: HomeworkAnalysisResult? = null,
    val errorMessage: String? = null,
    val selectedGrade: Grade = Grade.GRADE_3,
    val selectedSubject: Subject = Subject.MATH
)

@HiltViewModel
class HomeworkCorrectionViewModel @Inject constructor(
    private val repository: HomeworkCorrectionRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeworkCorrectionUiState())
    val uiState: StateFlow<HomeworkCorrectionUiState> = _uiState.asStateFlow()
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }
    
    fun analyzeHomework(imageUri: Uri) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            
            try {
                // Use real API call instead of mock
                val result = repository.analyzeHomework(
                    imageUri = imageUri,
                    grade = _uiState.value.selectedGrade,
                    subject = _uiState.value.selectedSubject
                )
                
                result.fold(
                    onSuccess = { response ->
                        if (response.success && response.data != null) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                analysisResult = response.data,
                                errorMessage = null
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = response.message ?: "分析失败"
                            )
                        }
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "网络错误"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "分析过程中发生错误"
                )
            }
        }
    }
    
    fun updateGrade(grade: Grade) {
        _uiState.value = _uiState.value.copy(selectedGrade = grade)
    }
    
    fun updateSubject(subject: Subject) {
        _uiState.value = _uiState.value.copy(selectedSubject = subject)
    }
    
    fun clearAnalysisResult() {
        _uiState.value = _uiState.value.copy(
            analysisResult = null,
            errorMessage = null
        )
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
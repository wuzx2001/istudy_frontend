package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.api.UploadService
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.model.LearningReport
import com.seecolab.istudy.data.model.StudentByRoleData
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.data.repository.AuthRepository
import com.seecolab.istudy.data.repository.HomeworkRepository
import com.seecolab.istudy.data.repository.StudentRepository
import com.seecolab.istudy.data.repository.UploadRepository
import com.seecolab.istudy.data.repository.UserRepository
import com.seecolab.istudy.utils.ApiErrorHandler
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ParentZoneUiState(
    val learningReport: LearningReport? = null,
    val students: List<StudentByRoleData> = emptyList(),
    val selectedStudent: StudentByRoleData? = null,
    val studentWorkList: List<StudentWorkListItem> = emptyList(),
    val isLoading: Boolean = true,
    val isLoadingWorkList: Boolean = false,
    val isDeleting: Boolean = false,
    val errorMessage: String? = null,
    val deleteMessage: String? = null
)

@HiltViewModel
class ParentZoneViewModel @Inject constructor(
    private val homeworkRepository: HomeworkRepository,
    private val studentRepository: StudentRepository,
    private val userRepository: UserRepository,
    private val uploadService: UploadService,
    private val uploadRepository: UploadRepository,
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager,
    private val apiErrorHandler: ApiErrorHandler,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParentZoneUiState())
    val uiState: StateFlow<ParentZoneUiState> = _uiState.asStateFlow()
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }

    fun loadLearningReport() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                // Load students using the new role-specific endpoint
                val studentsResult = userRepository.getStudentsByRole()
                val students = studentsResult.getOrNull() ?: emptyList()
                
                val currentStudent = studentRepository.getCurrentStudent()
                val report = if (currentStudent != null) {
                    homeworkRepository.getLatestReport(currentStudent.id)
                } else null
                
                _uiState.value = _uiState.value.copy(
                    learningReport = report,
                    students = students,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "加载学习报告失败: ${e.message}"
                )
            }
        }
    }
    
    fun selectStudent(student: StudentByRoleData) {
        _uiState.value = _uiState.value.copy(
            selectedStudent = student,
            studentWorkList = emptyList(), // Clear previous work list
            errorMessage = null
        )
        loadStudentWorkList(student.user_id)
    }
    
    private fun loadStudentWorkList(studentId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoadingWorkList = true)
                
                val token = tokenManager.getBearerToken()
                if (token == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoadingWorkList = false,
                        errorMessage = "Authentication token not found"
                    )
                    return@launch
                }
                
                // Use ApiErrorHandler to handle authentication errors
                val result = apiErrorHandler.executeWithAuthHandling {
                    uploadService.getStudentWorkList(
                        studentId = studentId,
                        page = 1,
                        pageSize = 20, // Show more items for parents
                        startDate = null,
                        endDate = null,
                        authorization = token
                    )
                }
                
                result.fold(
                    onSuccess = { workListResponse ->
                        if (workListResponse.success) {
                            _uiState.value = _uiState.value.copy(
                                isLoadingWorkList = false,
                                studentWorkList = workListResponse.works,
                                errorMessage = null
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoadingWorkList = false,
                                errorMessage = workListResponse.message ?: "Failed to load work list"
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoadingWorkList = false,
                            errorMessage = error.message ?: "Failed to load work list"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingWorkList = false,
                    errorMessage = "Error: ${e.message}"
                )
            }
        }
    }
    
    fun deleteStudentWork(workItem: StudentWorkListItem) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isDeleting = true,
                    deleteMessage = null,
                    errorMessage = null
                )
                
                val result = uploadRepository.deleteStudentWork(workItem.work_id)
                
                result.fold(
                    onSuccess = { deleteResponse ->
                        if (deleteResponse.success) {
                            // Remove the deleted item from the list
                            val updatedWorkList = _uiState.value.studentWorkList.filter { 
                                it.work_id != workItem.work_id 
                            }
                            
                            _uiState.value = _uiState.value.copy(
                                isDeleting = false,
                                studentWorkList = updatedWorkList,
                                deleteMessage = deleteResponse.message
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isDeleting = false,
                                errorMessage = deleteResponse.message
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isDeleting = false,
                            errorMessage = error.message ?: "Failed to delete work"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isDeleting = false,
                    errorMessage = "Error: ${e.message}"
                )
            }
        }
    }
    
    fun clearDeleteMessage() {
        _uiState.value = _uiState.value.copy(deleteMessage = null)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.api.UploadService
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.data.model.StudentWorkListResponse
import com.seecolab.istudy.data.model.UserRole
import com.seecolab.istudy.data.repository.AuthRepository
import com.seecolab.istudy.data.repository.UploadRepository
import com.seecolab.istudy.utils.ApiErrorHandler
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class StudentWorkListUiState(
    val isLoading: Boolean = false,
    val workList: List<StudentWorkListItem> = emptyList(),
    val totalCount: Int = 0,
    val currentPage: Int = 1,
    val pageSize: Int = 10,
    val startDate: String = "",
    val endDate: String = "",
    val errorMessage: String? = null,
    val hasMorePages: Boolean = false,
    val currentUser: com.seecolab.istudy.data.model.User? = null,
    val isDeleting: Boolean = false,
    val deleteMessage: String? = null,
    // 新增：学科筛选
    val selectedSubject: String? = null
)

@HiltViewModel
class StudentWorkListViewModel @Inject constructor(
    private val uploadService: UploadService,
    private val uploadRepository: UploadRepository,
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager,
    private val authenticationGuard: AuthenticationGuard,
    private val apiErrorHandler: ApiErrorHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentWorkListUiState())
    val uiState: StateFlow<StudentWorkListUiState> = _uiState.asStateFlow()

    // 保存全量作业列表用于本地筛选
    private var allWorks: MutableList<StudentWorkListItem> = mutableListOf()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
        loadUserInfo()
        loadWorkList()
    }
    
    private fun loadUserInfo() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            _uiState.value = _uiState.value.copy(currentUser = currentUser)
        }
    }

    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }

    fun loadWorkList(
        page: Int = 1,
        pageSize: Int = 10,
        startDate: String? = null,
        endDate: String? = null,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

                val user = authRepository.getCurrentUser()
                val userId = tokenManager.getUserId()
                if (user == null || userId == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "User not logged in"
                    )
                    return@launch
                }

                val token = tokenManager.getBearerToken()
                if (token == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Authentication token not found"
                    )
                    return@launch
                }

                // For student users, only get last 10 works
                val finalPageSize = if (user.role == UserRole.STUDENT) 10 else pageSize
                val finalPage = if (user.role == UserRole.STUDENT) 1 else page

                // Use ApiErrorHandler to handle authentication errors
                val result = apiErrorHandler.executeWithAuthHandling {
                    uploadService.getStudentWorkList(
                        studentId = userId,
                        page = finalPage,
                        pageSize = finalPageSize,
                        startDate = startDate,
                        endDate = endDate,
                        authorization = token
                    )
                }
                
                result.fold(
                    onSuccess = { workListResponse ->
                        if (workListResponse.success) {
                            // 维护全量数据，再应用本地学科筛选
                            if (isRefresh || page == 1) {
                                allWorks = workListResponse.works.toMutableList()
                            } else {
                                allWorks.addAll(workListResponse.works)
                            }

                            val filtered = applySubjectFilter(allWorks, _uiState.value.selectedSubject)

                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                workList = filtered,
                                totalCount = workListResponse.total_count,
                                currentPage = finalPage,
                                pageSize = finalPageSize,
                                startDate = startDate ?: "",
                                endDate = endDate ?: "",
                                hasMorePages = filtered.size < workListResponse.total_count,
                                errorMessage = null
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = workListResponse.message ?: "Failed to load work list"
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load work list"
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

    fun loadMoreWorks() {
        val currentState = _uiState.value
        if (!currentState.isLoading && currentState.hasMorePages) {
            loadWorkList(
                page = currentState.currentPage + 1,
                pageSize = currentState.pageSize,
                startDate = currentState.startDate.takeIf { it.isNotEmpty() },
                endDate = currentState.endDate.takeIf { it.isNotEmpty() }
            )
        }
    }

    fun refreshWorkList() {
        val currentState = _uiState.value
        loadWorkList(
            page = 1,
            pageSize = currentState.pageSize,
            startDate = currentState.startDate.takeIf { it.isNotEmpty() },
            endDate = currentState.endDate.takeIf { it.isNotEmpty() },
            isRefresh = true
        )
    }

    fun searchByDateRange(startDate: String, endDate: String) {
        // Validate date format
        try {
            if (startDate.isNotEmpty()) {
                dateFormat.parse(startDate)
            }
            if (endDate.isNotEmpty()) {
                dateFormat.parse(endDate)
            }
            
            loadWorkList(
                page = 1,
                startDate = startDate.takeIf { it.isNotEmpty() },
                endDate = endDate.takeIf { it.isNotEmpty() },
                isRefresh = true
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid date format. Please use YYYY-MM-DD format."
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    // 应用学科筛选到现有的全量数据
    private fun applySubjectFilter(
        works: List<StudentWorkListItem>,
        subject: String?
    ): List<StudentWorkListItem> {
        if (subject.isNullOrEmpty()) return works
        return works.filter { it.subject?.equals(subject, ignoreCase = false) == true }
    }

    // 设置学科筛选并立刻应用到列表
    fun setSubject(subject: String?) {
        _uiState.value = _uiState.value.copy(selectedSubject = subject)
        val filtered = applySubjectFilter(allWorks, subject)
        _uiState.value = _uiState.value.copy(workList = filtered)
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
                            val updatedWorkList = _uiState.value.workList.filter { 
                                it.work_id != workItem.work_id 
                            }
                            
                            _uiState.value = _uiState.value.copy(
                                isDeleting = false,
                                workList = updatedWorkList,
                                totalCount = _uiState.value.totalCount - 1,
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
}
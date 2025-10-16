package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.api.UploadService
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.data.model.StudentWorkListResponse
import com.seecolab.istudy.utils.ApiErrorHandler
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UploadWorkListUiState(
    val isLoading: Boolean = false,
    val list: List<StudentWorkListItem> = emptyList(),
    val errorMessage: String? = null,
    // filters
    val studentId: String = "",
    val subject: String? = null,
    val startDate: String = "",
    val endDate: String = "",
    val maxScore: String = "",
    // date picker
    val isDatePickerVisible: Boolean = false,
    val isStartPicker: Boolean = true
)

@HiltViewModel
class UploadWorkListViewModel @Inject constructor(
    private val uploadService: UploadService,
    private val tokenManager: TokenManager,
    private val apiErrorHandler: ApiErrorHandler,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {

    private val _uiState = MutableStateFlow(UploadWorkListUiState())
    val uiState: StateFlow<UploadWorkListUiState> = _uiState.asStateFlow()

    init {
        // 验证登录
        viewModelScope.launch { authenticationGuard.verifyAuthentication() }
    }

    fun initStudent(studentId: String) {
        _uiState.value = _uiState.value.copy(studentId = studentId)
    }

    fun updateSubject(subject: String?) { _uiState.value = _uiState.value.copy(subject = subject) }
    fun updateStartDate(date: String) { _uiState.value = _uiState.value.copy(startDate = date) }
    fun updateEndDate(date: String) { _uiState.value = _uiState.value.copy(endDate = date) }
    fun updateMaxScore(score: String) { _uiState.value = _uiState.value.copy(maxScore = score) }

    fun showDatePicker(isStart: Boolean) {
        _uiState.value = _uiState.value.copy(isDatePickerVisible = true, isStartPicker = isStart)
    }
    fun hideDatePicker() {
        _uiState.value = _uiState.value.copy(isDatePickerVisible = false)
    }

    fun resetAndSearch() {
        _uiState.value = _uiState.value.copy(subject = null, startDate = "", endDate = "", maxScore = "")
        search()
    }

    fun search() {
        val state = _uiState.value
        viewModelScope.launch {
            try {
                _uiState.value = state.copy(isLoading = true, errorMessage = null)

                val token = tokenManager.getBearerToken()
                if (token == null) {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Authentication token not found")
                    return@launch
                }

                val result = apiErrorHandler.executeWithAuthHandling {
                    uploadService.getStudentWorksByFilters(
                        studentId = state.studentId,
                        subject = state.subject,
                        startDate = state.startDate.takeIf { it.isNotEmpty() },
                        endDate = state.endDate.takeIf { it.isNotEmpty() },
                        maxScore = state.maxScore.toIntOrNull(),
                        authorization = token
                    )
                }

                result.fold(
                    onSuccess = { resp: StudentWorkListResponse ->
                        if (resp.success) {
                            _uiState.value = _uiState.value.copy(isLoading = false, list = resp.works, errorMessage = null)
                        } else {
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = resp.message ?: "加载失败")
                        }
                    },
                    onFailure = { e ->
                        _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "加载失败")
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "加载失败")
            }
        }
    }
}
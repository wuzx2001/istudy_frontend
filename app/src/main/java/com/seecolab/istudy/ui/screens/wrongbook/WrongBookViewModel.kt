package com.seecolab.istudy.ui.screens.wrongbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.data.repository.UploadRepository
import com.seecolab.istudy.data.repository.AuthRepository
import com.seecolab.istudy.data.model.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WrongBookUiState(
    val subject: String = "",
    val startDate: String? = null,
    val endDate: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val results: List<StudentWorkListItem> = emptyList(),
    val studentId: String? = null
)

@HiltViewModel
class WrongBookViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WrongBookUiState())
    val uiState: StateFlow<WrongBookUiState> = _uiState

    init {
        // 学生角色：自动使用自身 userId；家长角色：等待从导航或选择学生设置
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            val userId = tokenManager.getUserId()
            if (user?.role == UserRole.STUDENT && !userId.isNullOrBlank()) {
                _uiState.value = _uiState.value.copy(studentId = userId)
            }
        }
    }

    fun updateSubject(subject: String) {
        _uiState.value = _uiState.value.copy(subject = subject)
    }

    fun updateStartDate(date: String?) {
        _uiState.value = _uiState.value.copy(startDate = date)
    }

    fun updateEndDate(date: String?) {
        _uiState.value = _uiState.value.copy(endDate = date)
    }

    fun setStudentId(id: String?) {
        if (!id.isNullOrBlank()) {
            _uiState.value = _uiState.value.copy(studentId = id)
        }
    }

    fun clearFilters() {
        _uiState.value = _uiState.value.copy(
            subject = "",
            startDate = null,
            endDate = null,
            errorMessage = null,
            results = emptyList()
        )
    }

    fun searchWrongQuestions() {
        val state = _uiState.value
        val studentId = state.studentId
        if (studentId.isNullOrBlank()) {
            _uiState.value = state.copy(errorMessage = "未找到学生ID，请登录学生账号或选择学生。")
            return
        }
        if (state.subject.isBlank()) {
            _uiState.value = state.copy(errorMessage = "请填写学科，例如：语文、数学、英语。")
            return
        }

        _uiState.value = state.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            val result = uploadRepository.getWrongQuestions(
                studentId = studentId,
                subject = state.subject,
                startDate = state.startDate,
                endDate = state.endDate
            )
            result.fold(
                onSuccess = { resp ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        results = resp.works,
                        errorMessage = null
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "查询失败"
                    )
                }
            )
        }
    }
}
package com.seecolab.istudy.ui.screens.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.api.TeacherService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TeacherDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val name: String = "",
    val sex: String = "",
    val subjects: List<String> = emptyList(),
    val grades: List<String> = emptyList(),
    val address: String = "",
    val telephone: String = ""
)

@HiltViewModel
class TeacherDetailViewModel @Inject constructor(
    private val teacherService: TeacherService
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherDetailUiState(isLoading = true))
    val uiState: StateFlow<TeacherDetailUiState> = _uiState

    fun load(userId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val resp = teacherService.getTeacherById(userId)
                if (resp.isSuccessful) {
                    val data = resp.body()?.data
                    if (data != null) {
                        _uiState.value = TeacherDetailUiState(
                            isLoading = false,
                            name = data.name,
                            sex = when (data.gender) {
                                com.seecolab.istudy.data.model.Gender.MALE -> "男"
                                com.seecolab.istudy.data.model.Gender.FEMALE -> "女"
                                else -> "未知"
                            },
                            subjects = data.subjects.map { it.displayName },
                            grades = emptyList(),
                            address = data.location,
                            telephone = "" // 如后端返回电话字段在 Teacher 模型外，可后续扩展
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "教师详情为空")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "HTTP ${resp.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message ?: "请求失败")
            }
        }
    }
}
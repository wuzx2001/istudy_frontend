package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.seecolab.istudy.data.model.Teacher
import com.seecolab.istudy.data.repository.TeacherRepository
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TeacherRecommendationUiState(
    val teachers: List<Teacher> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    // 筛选状态
    val selectedSubject: String? = null,
    val selectedGender: String? = null,
    val selectedRegion: String? = null,
    val page: Int = 1,
    val pageSize: Int = 20
)

@HiltViewModel
class TeacherRecommendationViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherRecommendationUiState())
    val uiState: StateFlow<TeacherRecommendationUiState> = _uiState.asStateFlow()

    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
        // 首次进入即按默认筛选从服务端查询
        fetchFromServer()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }

    private fun fetchFromServer() {
        val subject = _uiState.value.selectedSubject
        val gender = _uiState.value.selectedGender
        val region = _uiState.value.selectedRegion
        val page = _uiState.value.page
        val pageSize = _uiState.value.pageSize

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        Log.d("TeacherVM", "search start | subject=$subject, gender=$gender, region=$region, page=$page, size=$pageSize")
        viewModelScope.launch {
            try {
                // 通过仓库查询（请在 TeacherRepository 实现对应方法；若已存在则直接调用）
                val result = teacherRepository.searchTeachers(
                    sex = gender,
                    subject = subject,
                    region = region,
                    page = page,
                    pageSize = pageSize
                )
                Log.d("TeacherVM", "search success | resultCount=${result.size}")
                _uiState.value = _uiState.value.copy(
                    teachers = result,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                Log.e("TeacherVM", "search failed: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "加载老师列表失败: ${e.message}"
                )
            }
        }
    }

    // 本地过滤逻辑已改为服务端查询，删除

    fun setSubject(subject: String?) {
        _uiState.value = _uiState.value.copy(selectedSubject = subject, page = 1)
        fetchFromServer()
    }

    fun setGender(gender: String?) {
        _uiState.value = _uiState.value.copy(selectedGender = gender, page = 1)
        fetchFromServer()
    }

    fun setRegion(region: String?) {
        _uiState.value = _uiState.value.copy(selectedRegion = region, page = 1)
        fetchFromServer()
    }

    fun bookTeacher(teacherId: Long) {
        // TODO: Implement booking logic
        viewModelScope.launch {
            try {
                // Create booking logic here
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "预约失败: ${e.message}"
                )
            }
        }
    }
}
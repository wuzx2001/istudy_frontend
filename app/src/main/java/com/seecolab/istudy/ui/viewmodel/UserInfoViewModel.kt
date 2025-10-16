package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.data.repository.UserRepository
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserInfoUiState(
    val isLoading: Boolean = false,
    val parents: List<ParentByRoleData> = emptyList(),
    val students: List<StudentByRoleData> = emptyList(),
    val message: String? = null,
    val error: String? = null
)

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationGuard: AuthenticationGuard
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UserInfoUiState())
    val uiState: StateFlow<UserInfoUiState> = _uiState.asStateFlow()
    
    init {
        // Verify authentication when ViewModel is created
        verifyAuthentication()
        loadUserInfo()
    }
    
    private fun verifyAuthentication() {
        viewModelScope.launch {
            authenticationGuard.verifyAuthentication()
        }
    }
    
    fun loadUserInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            
            try {
                // Load parents and students using the new role-specific endpoints
                val parentsResult = userRepository.getParentsByRole()
                val studentsResult = userRepository.getStudentsByRole()
                
                val parents = parentsResult.getOrElse { emptyList() }
                val students = studentsResult.getOrElse { emptyList() }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    parents = parents,
                    students = students,
                    error = null
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "加载用户信息失败：${e.message}"
                )
            }
        }
    }
    
    fun deleteParent(parentId: String) {
        viewModelScope.launch {
            try {
                // Note: No deleteParent method available in current API
                // This would need to be implemented in the backend and UserRepository
                _uiState.value = _uiState.value.copy(
                    error = "删除家长功能暂未实现"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "删除家长失败：${e.message}"
                )
            }
        }
    }
    
    fun deleteStudent(studentId: String) {
        viewModelScope.launch {
            try {
                val result = userRepository.deleteStudent(studentId)
                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            message = "学生删除成功",
                            students = _uiState.value.students.filter { it.user_id != studentId }
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            error = "删除学生失败：${error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "删除学生失败：${e.message}"
                )
            }
        }
    }
    
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun refresh() {
        loadUserInfo()
    }
}
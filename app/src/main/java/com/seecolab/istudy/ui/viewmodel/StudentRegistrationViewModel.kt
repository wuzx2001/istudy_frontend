package com.seecolab.istudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seecolab.istudy.data.model.Gender
import com.seecolab.istudy.data.model.Grade
import com.seecolab.istudy.data.model.Student
import com.seecolab.istudy.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentRegistrationUiState(
    val name: String = "",
    val age: String = "",
    val gender: Gender? = null,
    val grade: Grade? = null,
    val isLoading: Boolean = false,
    val isRegistrationComplete: Boolean = false,
    val errorMessage: String? = null,
    val nameError: String? = null,
    val ageError: String? = null,
    val gradeError: String? = null
)

@HiltViewModel
class StudentRegistrationViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentRegistrationUiState())
    val uiState: StateFlow<StudentRegistrationUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            nameError = null
        )
    }

    fun updateAge(age: String) {
        // Only allow numeric input
        if (age.all { it.isDigit() } || age.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                age = age,
                ageError = null
            )
        }
    }

    fun updateGender(gender: Gender) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun updateGrade(grade: Grade) {
        _uiState.value = _uiState.value.copy(
            grade = grade,
            gradeError = null
        )
    }

    fun registerStudent() {
        if (validateInput()) {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            viewModelScope.launch {
                try {
                    val currentState = _uiState.value
                    val student = Student(
                        name = currentState.name.trim(),
                        age = currentState.age.toInt(),
                        gender = currentState.gender!!,
                        grade = currentState.grade!!
                    )
                    
                    studentRepository.insertStudent(student)
                    
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRegistrationComplete = true
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "注册失败: ${e.message}"
                    )
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        val currentState = _uiState.value
        var isValid = true
        var nameError: String? = null
        var ageError: String? = null
        var gradeError: String? = null

        // Validate name
        if (currentState.name.trim().isEmpty()) {
            nameError = "请输入学生姓名"
            isValid = false
        }

        // Validate age
        if (currentState.age.isEmpty()) {
            ageError = "请输入年龄"
            isValid = false
        } else {
            val ageInt = currentState.age.toIntOrNull()
            if (ageInt == null || ageInt < 5 || ageInt > 20) {
                ageError = "年龄必须在5-20岁之间"
                isValid = false
            }
        }

        // Validate grade
        if (currentState.grade == null) {
            gradeError = "请选择年级"
            isValid = false
        }

        _uiState.value = _uiState.value.copy(
            nameError = nameError,
            ageError = ageError,
            gradeError = gradeError
        )

        return isValid
    }
}
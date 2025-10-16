package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.local.dao.StudentDao
import com.seecolab.istudy.data.model.Student
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(
    private val studentDao: StudentDao
) {
    suspend fun getStudentById(id: Long): Student? {
        return studentDao.getStudentById(id)
    }

    fun getAllStudents(): Flow<List<Student>> {
        return studentDao.getAllStudents()
    }

    suspend fun insertStudent(student: Student): Long {
        return studentDao.insertStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun getCurrentStudent(): Student? {
        return studentDao.getCurrentStudent()
    }
}
package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.local.dao.CourseDao
import com.seecolab.istudy.data.local.dao.CourseProgressDao
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepository @Inject constructor(
    private val courseDao: CourseDao,
    private val progressDao: CourseProgressDao
) {
    suspend fun getCourseById(id: Long): Course? {
        return courseDao.getCourseById(id)
    }

    fun getCoursesBySubjectAndGrade(subject: Subject, grade: Grade): Flow<List<Course>> {
        return courseDao.getCoursesBySubjectAndGrade(subject, grade)
    }

    fun getCoursesByGrade(grade: Grade): Flow<List<Course>> {
        return courseDao.getCoursesByGrade(grade)
    }

    suspend fun insertCourse(course: Course): Long {
        return courseDao.insertCourse(course)
    }

    suspend fun insertCourses(courses: List<Course>) {
        courseDao.insertCourses(courses)
    }

    suspend fun updateCourse(course: Course) {
        courseDao.updateCourse(course)
    }

    suspend fun deleteCourse(course: Course) {
        courseDao.deleteCourse(course)
    }

    // Progress methods
    suspend fun getProgress(studentId: Long, courseId: Long): CourseProgress? {
        return progressDao.getProgress(studentId, courseId)
    }

    fun getStudentProgress(studentId: Long): Flow<List<CourseProgress>> {
        return progressDao.getStudentProgress(studentId)
    }

    suspend fun insertProgress(progress: CourseProgress): Long {
        return progressDao.insertProgress(progress)
    }

    suspend fun updateProgress(progress: CourseProgress) {
        progressDao.updateProgress(progress)
    }

    suspend fun deleteProgress(progress: CourseProgress) {
        progressDao.deleteProgress(progress)
    }

    suspend fun updateCourseProgress(
        studentId: Long,
        courseId: Long,
        newProgress: Float,
        timeSpent: Int,
        completedKeyPoints: List<String>
    ) {
        val existingProgress = getProgress(studentId, courseId)
        if (existingProgress != null) {
            val updatedProgress = existingProgress.copy(
                progress = newProgress,
                timeSpent = existingProgress.timeSpent + timeSpent,
                completedKeyPoints = completedKeyPoints,
                lastAccessedAt = System.currentTimeMillis()
            )
            updateProgress(updatedProgress)
        } else {
            val newProgressRecord = CourseProgress(
                studentId = studentId,
                courseId = courseId,
                progress = newProgress,
                timeSpent = timeSpent,
                completedKeyPoints = completedKeyPoints
            )
            insertProgress(newProgressRecord)
        }
    }
}
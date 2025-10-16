package com.seecolab.istudy.data.local.dao

import androidx.room.*
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: Long): Course?

    @Query("SELECT * FROM courses WHERE subject = :subject AND grade = :grade")
    fun getCoursesBySubjectAndGrade(subject: Subject, grade: Grade): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE grade = :grade")
    fun getCoursesByGrade(grade: Grade): Flow<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<Course>)

    @Update
    suspend fun updateCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)
}

@Dao
interface CourseProgressDao {
    @Query("SELECT * FROM course_progress WHERE studentId = :studentId AND courseId = :courseId")
    suspend fun getProgress(studentId: Long, courseId: Long): CourseProgress?

    @Query("SELECT * FROM course_progress WHERE studentId = :studentId")
    fun getStudentProgress(studentId: Long): Flow<List<CourseProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: CourseProgress): Long

    @Update
    suspend fun updateProgress(progress: CourseProgress)

    @Delete
    suspend fun deleteProgress(progress: CourseProgress)
}
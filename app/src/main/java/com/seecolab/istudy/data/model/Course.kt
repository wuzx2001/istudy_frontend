package com.seecolab.istudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "courses")
@Parcelize
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val subject: Subject,
    val grade: Grade,
    val chapterName: String,
    val description: String,
    val keyPoints: List<String>,
    val difficulty: Difficulty,
    val estimatedStudyTime: Int, // in minutes
    val prerequisites: List<String> = emptyList(),
    val learningObjectives: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Entity(tableName = "course_progress")
@Parcelize
data class CourseProgress(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val studentId: Long,
    val courseId: Long,
    val progress: Float, // 0.0 to 1.0
    val completedKeyPoints: List<String> = emptyList(),
    val timeSpent: Int, // in minutes
    val lastAccessedAt: Long = System.currentTimeMillis(),
    val startedAt: Long = System.currentTimeMillis()
) : Parcelable
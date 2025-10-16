package com.seecolab.istudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "learning_reports")
@Parcelize
data class LearningReport(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val studentId: Long,
    val reportPeriodStart: Long,
    val reportPeriodEnd: Long,
    val overallProgress: Float, // 0.0 to 1.0
    val subjectScores: Map<Subject, Float>,
    val completedTests: Int,
    val totalStudyTime: Int, // in minutes
    val strengthAreas: List<String>,
    val improvementAreas: List<String>,
    val recommendations: List<String>,
    val generatedAt: Long = System.currentTimeMillis()
) : Parcelable

@Entity(tableName = "study_sessions")
@Parcelize
data class StudySession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val studentId: Long,
    val subject: Subject,
    val activityType: ActivityType,
    val duration: Int, // in minutes
    val score: Float? = null, // for scored activities
    val completionRate: Float, // 0.0 to 1.0
    val startedAt: Long,
    val completedAt: Long = System.currentTimeMillis()
) : Parcelable

enum class ActivityType {
    HOMEWORK_CORRECTION, COURSE_STUDY, PRACTICE_TEST, EXAM_PREPARATION, TEACHER_SESSION
}
package com.seecolab.istudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "homework_submissions")
@Parcelize
data class HomeworkSubmission(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val studentId: Long,
    val subject: Subject,
    val imagePath: String,
    val analysisResult: AnalysisResult? = null,
    val aiRecommendations: List<String> = emptyList(),
    val generatedTestPaperId: Long? = null,
    val status: SubmissionStatus = SubmissionStatus.PENDING,
    val submittedAt: Long = System.currentTimeMillis(),
    val analyzedAt: Long? = null
) : Parcelable

@Parcelize
data class AnalysisResult(
    val detectedQuestions: List<DetectedQuestion>,
    val overallScore: Float, // 0.0 to 1.0
    val weakAreas: List<String>,
    val strengths: List<String>,
    val recommendedStudyPlan: StudyPlan
) : Parcelable

@Parcelize
data class DetectedQuestion(
    val questionNumber: Int,
    val questionText: String,
    val studentAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val explanation: String,
    val confidence: Float // AI confidence in detection
) : Parcelable

@Parcelize
data class StudyPlan(
    val targetAreas: List<String>,
    val recommendedCourses: List<Long>, // course IDs
    val estimatedStudyTime: Int, // in minutes
    val difficulty: Difficulty,
    val priority: Priority
) : Parcelable

enum class SubmissionStatus {
    PENDING, ANALYZING, COMPLETED, FAILED
}

enum class Priority {
    LOW, MEDIUM, HIGH, URGENT
}
package com.seecolab.istudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "questions")
@Parcelize
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val subject: Subject,
    val grade: Grade,
    val chapter: String,
    val questionText: String,
    val questionType: QuestionType,
    val options: List<String> = emptyList(), // for multiple choice
    val correctAnswer: String,
    val explanation: String,
    val difficulty: Difficulty,
    val points: Int = 1,
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Entity(tableName = "test_papers")
@Parcelize
data class TestPaper(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val subject: Subject,
    val grade: Grade,
    val examType: ExamType,
    val questionIds: List<Long>,
    val totalPoints: Int,
    val timeLimit: Int, // in minutes
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Entity(tableName = "test_submissions")
@Parcelize
data class TestSubmission(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val studentId: Long,
    val testPaperId: Long,
    val answers: Map<Long, String>, // questionId to answer
    val score: Int,
    val totalPoints: Int,
    val timeSpent: Int, // in minutes
    val submittedAt: Long = System.currentTimeMillis(),
    val startedAt: Long
) : Parcelable

enum class QuestionType {
    MULTIPLE_CHOICE, TRUE_FALSE, SHORT_ANSWER, ESSAY
}

enum class ExamType {
    UNIT, MIDTERM, FINAL, PRACTICE
}
package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.api.*
import com.seecolab.istudy.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIRepository @Inject constructor(
    private val aiService: AIService
) {
    suspend fun analyzeHomeworkImage(
        imagePath: String,
        subject: Subject,
        grade: Grade
    ): AIHomeworkAnalysisResponse {
        val request = AIHomeworkAnalysisRequest(
            imagePath = imagePath,
            subject = subject,
            grade = grade
        )
        return aiService.analyzeHomework(request)
    }

    suspend fun generateTestPaper(
        subject: Subject,
        grade: Grade,
        examType: ExamType,
        questionCount: Int = 20,
        difficulty: Difficulty? = null,
        focusAreas: List<String> = emptyList()
    ): TestGenerationResponse {
        val request = TestGenerationRequest(
            subject = subject,
            grade = grade,
            examType = examType,
            questionCount = questionCount,
            difficulty = difficulty,
            focusAreas = focusAreas
        )
        return aiService.generateTest(request)
    }

    suspend fun getLearningRecommendations(
        studentId: Long,
        subject: Subject,
        recentPerformance: List<TestSubmission>,
        weakAreas: List<String>
    ): LearningRecommendationResponse {
        val request = LearningRecommendationRequest(
            studentId = studentId,
            subject = subject,
            recentPerformance = recentPerformance,
            weakAreas = weakAreas
        )
        return aiService.getLearningRecommendations(request)
    }

    suspend fun getExamKnowledgePoints(
        subject: Subject,
        grade: Grade,
        examType: ExamType
    ): List<String> {
        return aiService.summarizeKnowledgePoints(subject, grade, examType)
    }
}
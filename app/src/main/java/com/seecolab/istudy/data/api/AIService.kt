package com.seecolab.istudy.data.api

import com.seecolab.istudy.data.model.*

data class AIHomeworkAnalysisRequest(
    val imagePath: String,
    val subject: Subject,
    val grade: Grade
)

data class AIHomeworkAnalysisResponse(
    val success: Boolean,
    val analysisResult: AnalysisResult?,
    val error: String?
)

data class TestGenerationRequest(
    val subject: Subject,
    val grade: Grade,
    val examType: ExamType,
    val questionCount: Int = 20,
    val difficulty: Difficulty? = null,
    val focusAreas: List<String> = emptyList()
)

data class TestGenerationResponse(
    val success: Boolean,
    val testPaper: TestPaper?,
    val questions: List<Question>?,
    val error: String?
)

data class LearningRecommendationRequest(
    val studentId: Long,
    val subject: Subject,
    val recentPerformance: List<TestSubmission>,
    val weakAreas: List<String>
)

data class LearningRecommendationResponse(
    val success: Boolean,
    val recommendations: List<String>,
    val studyPlan: StudyPlan?,
    val recommendedCourses: List<Long>,
    val error: String?
)

interface AIService {
    suspend fun analyzeHomework(request: AIHomeworkAnalysisRequest): AIHomeworkAnalysisResponse
    suspend fun generateTest(request: TestGenerationRequest): TestGenerationResponse
    suspend fun getLearningRecommendations(request: LearningRecommendationRequest): LearningRecommendationResponse
    suspend fun summarizeKnowledgePoints(subject: Subject, grade: Grade, examType: ExamType): List<String>
}
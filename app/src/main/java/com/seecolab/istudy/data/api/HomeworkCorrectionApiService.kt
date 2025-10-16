package com.seecolab.istudy.data.api

import com.seecolab.istudy.data.model.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

data class HomeworkAnalysisRequest(
    val image: MultipartBody.Part,
    val studentGrade: String,
    val subject: String
)

data class HomeworkAnalysisResponse(
    val success: Boolean,
    val data: HomeworkAnalysisResult?,
    val message: String?
)

data class HomeworkAnalysisResult(
    val problems: List<ProblemAnalysis>,
    val overallScore: Float,
    val suggestions: List<String>,
    val correctAnswers: List<String>,
    val analysisId: String
)

data class ProblemAnalysis(
    val questionNumber: Int,
    val isCorrect: Boolean,
    val studentAnswer: String,
    val correctAnswer: String,
    val explanation: String,
    val knowledgePoints: List<String>,
    val confidence: Float
)

interface HomeworkCorrectionApiService {
    
    @Multipart
    @POST("api/homework/analyze")
    suspend fun analyzeHomework(
        @Part image: MultipartBody.Part,
        @Part("grade") grade: RequestBody,
        @Part("subject") subject: RequestBody,
        @Part("language") language: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(), "zh-CN"
        )
    ): Response<HomeworkAnalysisResponse>
    
    @GET("api/homework/result/{analysisId}")
    suspend fun getAnalysisResult(
        @Path("analysisId") analysisId: String
    ): Response<HomeworkAnalysisResponse>
    
    @POST("api/homework/feedback")
    suspend fun submitFeedback(
        @Body feedback: HomeworkFeedback
    ): Response<ApiResponse<Unit>>
}

data class HomeworkFeedback(
    val analysisId: String,
    val rating: Int, // 1-5 stars
    val comment: String?
)

// Removed duplicate ApiResponse - using the one from data.model package
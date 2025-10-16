package com.seecolab.istudy.data.repository

import android.content.Context
import android.net.Uri
import com.seecolab.istudy.data.api.HomeworkAnalysisResponse
import com.seecolab.istudy.data.api.HomeworkCorrectionApiService
import com.seecolab.istudy.data.model.Grade
import com.seecolab.istudy.data.model.Subject
import com.seecolab.istudy.utils.ApiErrorHandler
import com.seecolab.istudy.utils.ImageUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeworkCorrectionRepository @Inject constructor(
    private val apiService: HomeworkCorrectionApiService,
    private val context: Context,
    private val apiErrorHandler: ApiErrorHandler
) {
    
    suspend fun analyzeHomework(
        imageUri: Uri,
        grade: Grade,
        subject: Subject
    ): Result<HomeworkAnalysisResponse> {
        return try {
            // Convert Uri to File and create MultipartBody.Part
            val imageFile = ImageUtils.uriToFile(context, imageUri)
            val requestFile = RequestBody.create(
                "image/*".toMediaTypeOrNull(),
                imageFile
            )
            val imagePart = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestFile
            )
            
            // Create request bodies for other parameters
            val gradeBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                grade.name
            )
            val subjectBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                subject.name
            )
            
            // Use ApiErrorHandler to handle authentication errors
            apiErrorHandler.executeWithAuthHandling {
                apiService.analyzeHomework(
                    image = imagePart,
                    grade = gradeBody,
                    subject = subjectBody
                )
            }
        } catch (e: Exception) {
            // Check if exception indicates auth failure
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getAnalysisResult(analysisId: String): Result<HomeworkAnalysisResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                apiService.getAnalysisResult(analysisId)
            }
        } catch (e: Exception) {
            // Check if exception indicates auth failure
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    // Mock implementation for testing when API is not available
    suspend fun mockAnalyzeHomework(
        imageUri: Uri,
        grade: Grade,
        subject: Subject
    ): Result<HomeworkAnalysisResponse> {
        return try {
            // Simulate network delay
            kotlinx.coroutines.delay(3000)
            
            val mockResponse = createMockAnalysisResponse(grade, subject)
            Result.success(mockResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun createMockAnalysisResponse(grade: Grade, subject: Subject): HomeworkAnalysisResponse {
        val problems = when (subject) {
            Subject.MATH -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = true,
                    studentAnswer = "12",
                    correctAnswer = "12",
                    explanation = "计算正确！3×4=12",
                    knowledgePoints = listOf("乘法运算"),
                    confidence = 0.95f
                ),
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 2,
                    isCorrect = false,
                    studentAnswer = "15",
                    correctAnswer = "18",
                    explanation = "计算错误。正确答案：6+12=18",
                    knowledgePoints = listOf("加法运算"),
                    confidence = 0.88f
                ),
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 3,
                    isCorrect = true,
                    studentAnswer = "24",
                    correctAnswer = "24",
                    explanation = "计算正确！8×3=24",
                    knowledgePoints = listOf("乘法运算"),
                    confidence = 0.92f
                )
            )
            Subject.CHINESE -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = true,
                    studentAnswer = "美丽",
                    correctAnswer = "美丽",
                    explanation = "词语运用正确",
                    knowledgePoints = listOf("形容词使用"),
                    confidence = 0.90f
                ),
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 2,
                    isCorrect = false,
                    studentAnswer = "的",
                    correctAnswer = "地",
                    explanation = "应该使用'地'而不是'的'，因为后面跟的是动词",
                    knowledgePoints = listOf("的地得用法"),
                    confidence = 0.85f
                )
            )
            Subject.ENGLISH -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = true,
                    studentAnswer = "apple",
                    correctAnswer = "apple",
                    explanation = "Spelling is correct!",
                    knowledgePoints = listOf("Vocabulary"),
                    confidence = 0.95f
                ),
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 2,
                    isCorrect = false,
                    studentAnswer = "is",
                    correctAnswer = "are",
                    explanation = "Use 'are' with plural nouns like 'books'",
                    knowledgePoints = listOf("Subject-verb agreement"),
                    confidence = 0.88f
                )
            )
            Subject.PHYSICS -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = true,
                    studentAnswer = "9.8m/s²",
                    correctAnswer = "9.8m/s²",
                    explanation = "重力加速度计算正确",
                    knowledgePoints = listOf("重力加速度"),
                    confidence = 0.92f
                )
            )
            Subject.CHEMISTRY -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = false,
                    studentAnswer = "H2O2",
                    correctAnswer = "H2SO4",
                    explanation = "硫酸的化学式是H2SO4，不是H2O2",
                    knowledgePoints = listOf("化学式"),
                    confidence = 0.88f
                )
            )
            Subject.BIOLOGY -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = true,
                    studentAnswer = "细胞壁",
                    correctAnswer = "细胞壁",
                    explanation = "植物细胞特有的结构识别正确",
                    knowledgePoints = listOf("细胞结构"),
                    confidence = 0.90f
                )
            )
            Subject.HISTORY -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = false,
                    studentAnswer = "1840年",
                    correctAnswer = "1842年",
                    explanation = "《南京条约》签订时间是1842年",
                    knowledgePoints = listOf("近代史"),
                    confidence = 0.85f
                )
            )
            Subject.GEOGRAPHY -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = true,
                    studentAnswer = "长江",
                    correctAnswer = "长江",
                    explanation = "中国最长的河流识别正确",
                    knowledgePoints = listOf("中国地理"),
                    confidence = 0.95f
                )
            )
            Subject.POLITICS -> listOf(
                com.seecolab.istudy.data.api.ProblemAnalysis(
                    questionNumber = 1,
                    isCorrect = true,
                    studentAnswer = "人民代表大会",
                    correctAnswer = "人民代表大会",
                    explanation = "我国的根本政治制度识别正确",
                    knowledgePoints = listOf("政治制度"),
                    confidence = 0.90f
                )
            )
        }
        
        val overallScore = problems.count { it.isCorrect }.toFloat() / problems.size * 100
        
        val suggestions = when {
            overallScore >= 90 -> listOf(
                "做得很好！继续保持这种学习状态",
                "可以尝试更有挑战性的题目",
                "注意保持书写工整"
            )
            overallScore >= 70 -> listOf(
                "基础掌握不错，需要加强练习",
                "重点复习错误的知识点",
                "建议多做类似题型的练习"
            )
            else -> listOf(
                "需要加强基础知识的学习",
                "建议重新学习相关知识点",
                "可以寻求老师或同学的帮助",
                "多做基础练习题"
            )
        }
        
        return HomeworkAnalysisResponse(
            success = true,
            data = com.seecolab.istudy.data.api.HomeworkAnalysisResult(
                problems = problems,
                overallScore = overallScore,
                suggestions = suggestions,
                correctAnswers = problems.map { it.correctAnswer },
                analysisId = "mock_${System.currentTimeMillis()}"
            ),
            message = "分析完成"
        )
    }
}
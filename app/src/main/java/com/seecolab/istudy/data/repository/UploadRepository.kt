package com.seecolab.istudy.data.repository

import android.content.Context
import android.net.Uri
import com.seecolab.istudy.data.api.UploadService
import com.seecolab.istudy.data.model.DeleteStudentWorkResponse
import com.seecolab.istudy.data.model.StudentWorkResponse
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.utils.ApiErrorHandler
import com.seecolab.istudy.utils.ImageUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadRepository @Inject constructor(
    private val uploadService: UploadService,
    private val tokenManager: TokenManager,
    private val context: Context,
    private val apiErrorHandler: ApiErrorHandler
) {
    
    suspend fun getWrongDetail(
        workId: String
    ): Result<com.seecolab.istudy.data.model.WrongDetailResponse> {
        return try {
            val token = tokenManager.getBearerToken()
            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("Authentication required"))
            }
            apiErrorHandler.executeWithAuthHandling {
                uploadService.getWrongDetail(
                    workId = workId,
                    authorization = token
                )
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(Exception("Query error: ${e.message}"))
            }
        }
    }
    
    suspend fun getWrongQuestions(
        studentId: String,
        subject: String,
        startDate: String? = null,
        endDate: String? = null
    ): Result<com.seecolab.istudy.data.model.StudentWorkListResponse> {
        return try {
            val token = tokenManager.getBearerToken()
            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("Authentication required"))
            }
            apiErrorHandler.executeWithAuthHandling {
                uploadService.getWrongQuestions(
                    studentId = studentId,
                    subject = subject,
                    startDate = startDate,
                    endDate = endDate,
                    authorization = token
                )
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(Exception("Query error: ${e.message}"))
            }
        }
    }
    
    suspend fun uploadPaperWork(
        imageUris: List<Uri>,
        timeConsumingMinutes: Int
    ): Result<StudentWorkResponse> {
        return try {
            // Get auth token
            val token = tokenManager.getBearerToken()
            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("Authentication required"))
            }
            
            // Convert URIs to Files and create MultipartBody.Parts
            val imageParts = imageUris.map { uri ->
                val imageFile = ImageUtils.uriToFile(context, uri)
                val requestFile = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    imageFile
                )
                MultipartBody.Part.createFormData(
                    "files",
                    imageFile.name,
                    requestFile
                )
            }
            
            // 创建 time_consuming 文本字段
            val timeConsumingBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                timeConsumingMinutes.toString()
            )
            // Use ApiErrorHandler to handle authentication errors
            apiErrorHandler.executeWithAuthHandling {
                uploadService.uploadPaperWork(
                    files = imageParts,
                    timeConsuming = timeConsumingBody,
                    authorization = token
                )
            }
        } catch (e: Exception) {
            // Check if exception indicates auth failure
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(Exception("Upload error: ${e.message}"))
            }
        }
    }
    
    suspend fun updateAiCorrection(
        workId: String,
        subject: String,
        paperName: String
    ): Result<StudentWorkResponse> {
        return try {
            val token = tokenManager.getBearerToken()
            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("Authentication required"))
            }
            
            // Use ApiErrorHandler to handle authentication errors
            apiErrorHandler.executeWithAuthHandling {
                uploadService.updateAiCorrection(
                    workId = workId,
                    subject = subject,
                    paperName = paperName,
                    authorization = token
                )
            }
        } catch (e: Exception) {
            // Check if exception indicates auth failure
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(Exception("Update error: ${e.message}"))
            }
        }
    }
    
    suspend fun deleteStudentWork(
        workId: String
    ): Result<DeleteStudentWorkResponse> {
        return try {
            val token = tokenManager.getBearerToken()
            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("Authentication required"))
            }
            
            // Use ApiErrorHandler to handle authentication errors
            apiErrorHandler.executeWithAuthHandling {
                uploadService.deleteStudentWork(
                    workId = workId,
                    authorization = token
                )
            }
        } catch (e: Exception) {
            // Check if exception indicates auth failure
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(Exception("Delete error: ${e.message}"))
            }
        }
    }
}
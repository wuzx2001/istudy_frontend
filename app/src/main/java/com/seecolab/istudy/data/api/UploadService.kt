package com.seecolab.istudy.data.api

import com.seecolab.istudy.data.model.DeleteStudentWorkResponse
import com.seecolab.istudy.data.model.StudentWorkListResponse
import com.seecolab.istudy.data.model.StudentWorkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UploadService {
    
    @Multipart
    @POST("files/upload-paper-work")
    suspend fun uploadPaperWork(
        @Part files: List<MultipartBody.Part>,
        @Part("time_consuming") timeConsuming: RequestBody,
        @Header("Authorization") authorization: String
    ): Response<StudentWorkResponse>
    
    @GET("student-works/list/{student_id}")
    suspend fun getStudentWorkList(
        @Path("student_id") studentId: String,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Header("Authorization") authorization: String
    ): Response<StudentWorkListResponse>

    // 家长专区-带筛选的作业列表
    @GET("student-works/student/{id}")
    suspend fun getStudentWorksByFilters(
        @Path("id") studentId: String,
        @Query("subject") subject: String? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("max_score") maxScore: Int? = null,
        @Header("Authorization") authorization: String
    ): Response<StudentWorkListResponse>
    
    @GET("student-works/{work_id}")
    suspend fun getStudentWork(
        @Path("work_id") workId: String,
        @Header("Authorization") authorization: String
    ): Response<StudentWorkResponse>
    
    @PUT("student-works/{work_id}/ai-correction")
    suspend fun updateAiCorrection(
        @Path("work_id") workId: String,
        @Query("subject") subject: String,
        @Query("paper_name") paperName: String,
        @Header("Authorization") authorization: String
    ): Response<StudentWorkResponse>
    
    @DELETE("student-works/{work_id}")
    suspend fun deleteStudentWork(
        @Path("work_id") workId: String,
        @Header("Authorization") authorization: String
    ): Response<DeleteStudentWorkResponse>
    
    // 错题本筛选
    @GET("student-works/wrong-questions")
    suspend fun getWrongQuestions(
        @Query("student_id") studentId: String,
        @Query("subject") subject: String,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Header("Authorization") authorization: String
    ): Response<StudentWorkListResponse>
    
    // 错题详情
    @GET("student-works/wrong-detail/{work_id}")
    suspend fun getWrongDetail(
        @Path("work_id") workId: String,
        @Header("Authorization") authorization: String
    ): Response<com.seecolab.istudy.data.model.WrongDetailResponse>
}
package com.seecolab.istudy.data.api

import com.seecolab.istudy.data.model.ApiResponse
import com.seecolab.istudy.data.model.Teacher
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class TeacherRegisterRequest(
    val user_name: String,
    val real_name: String,
    val sex: String,
    val grade: List<String>,      // e.g. ["grade_1","grade_3"]
    val subject: List<String>,    // e.g. ["语文","数学"]
    val telephone: String,
    val address: String,
    val password: String,
    val birthday: String          // "yyyy-MM-ddTHH:mm:ss"
)

data class TeacherSearchRequest(
    val sex: String? = null,
    val subject: String? = null,
    val region: String? = null,
    val page: Int? = 1,
    val page_size: Int? = 20
)

// Real response for /teachers/search
data class TeacherRemote(
    val user_id: String,
    val user_name: String,
    val real_name: String,
    val sex: String? = null,
    val subject: List<String> = emptyList(),
    val grade: List<String>? = null,
    val telephone: String? = null,
    val address: String? = null,
    val birthday: String? = null,
    val reg_date: String? = null,
    val deleted: Boolean? = null,
    val user_type: String? = null
)

data class TeacherSearchResponse(
    val success: Boolean,
    val teachers: List<TeacherRemote> = emptyList(),
    val total_count: Int? = null,
    val page: Int? = null,
    val page_size: Int? = null,
    val total_pages: Int? = null,
    val message: String? = null
)

interface TeacherService {
    // Register teacher
    @POST("teachers/register")
    suspend fun register(@Body request: TeacherRegisterRequest): Response<ApiResponse<String>>

    // Get one teacher by id (kept as-is; adjust if backend differs)
    @GET("teachers/{user_id}")
    suspend fun getTeacherById(@Path("user_id") userId: String): Response<ApiResponse<Teacher>>

    // Search teachers with conditions (server native response)
    @POST("teachers/search")
    suspend fun searchTeachers(@Body request: TeacherSearchRequest): Response<TeacherSearchResponse>
}
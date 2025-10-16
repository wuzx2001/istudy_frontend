package com.seecolab.istudy.data.api

import com.seecolab.istudy.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface UserManagementService {
    
    // Student Management Endpoints
    @POST("users/")
    suspend fun createStudent(@Body request: StudentCreate): Response<StudentResponse>
    
    @GET("users/{user_id}")
    suspend fun getStudent(@Path("user_id") userId: String): Response<StudentResponse>
    
    @GET("users/")
    suspend fun getAllStudents(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100
    ): Response<List<StudentResponse>>
    
    @PUT("users/{user_id}")
    suspend fun updateStudent(
        @Path("user_id") userId: String,
        @Body request: StudentUpdate
    ): Response<StudentResponse>
    
    @DELETE("users/{user_id}")
    suspend fun deleteStudent(@Path("user_id") userId: String): Response<Unit>
    
    // Parent Management Endpoints
    @POST("parents/")
    suspend fun createParent(@Body request: ParentCreate): Response<ParentResponse>
    
    @GET("parents/student/{student_user_id}")
    suspend fun getParentsForStudent(
        @Path("student_user_id") studentUserId: String
    ): Response<List<ParentResponse>>
    
    @POST("parents/add")
    suspend fun addParent(@Body request: AddParentRequest): Response<ParentResponse>
    
    @GET("users/related/{master_parent_id}")
    suspend fun getRelatedUsers(
        @Path("master_parent_id") masterParentId: String
    ): Response<RelatedUsersResponse>
    
    @GET("users/by-role/{role_type}")
    suspend fun getUsersByRole(
        @Path("role_type") roleType: String
    ): Response<UsersByRoleResponse>
    
    @GET("users/by-role/student")
    suspend fun getStudentsByRole(): Response<StudentsByRoleResponse>
    
    @GET("users/by-role/parent")
    suspend fun getParentsByRole(): Response<ParentsByRoleResponse>
    
    @POST("students/add")
    suspend fun addStudent(@Body request: AddStudentRequest): Response<StudentResponse>
    
    // File Management Endpoints
    @POST("files/submit-paper-work")
    suspend fun submitPaperWork(@Body request: PaperWorkSubmission): Response<StudentWorkResponse>
    
    @POST("files/request-access")
    suspend fun requestFileAccess(@Body request: FileAccessRequest): Response<FileUploadResponse>
}
package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.api.UserManagementService
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.utils.ApiErrorHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userManagementService: UserManagementService,
    private val apiErrorHandler: ApiErrorHandler
) {
    
    // Student Management Operations
    suspend fun createStudent(request: StudentCreate): Result<StudentResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.createStudent(request)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getStudent(userId: String): Result<StudentResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.getStudent(userId)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getAllStudents(skip: Int = 0, limit: Int = 100): Result<List<StudentResponse>> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.getAllStudents(skip, limit)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun updateStudent(userId: String, request: StudentUpdate): Result<StudentResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.updateStudent(userId, request)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun deleteStudent(userId: String): Result<Unit> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.deleteStudent(userId)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    // Parent Management Operations
    suspend fun createParent(request: ParentCreate): Result<ParentResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.createParent(request)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getParentsForStudent(studentUserId: String): Result<List<ParentResponse>> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.getParentsForStudent(studentUserId)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun addParent(request: AddParentRequest): Result<ParentResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.addParent(request)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getRelatedUsers(masterParentId: String): Result<RelatedUsersResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.getRelatedUsers(masterParentId)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun addStudent(request: AddStudentRequest): Result<StudentResponse> {
        return try {
            apiErrorHandler.executeWithAuthHandling {
                userManagementService.addStudent(request)
            }
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    // New role-specific user methods
    suspend fun getStudentsByRole(): Result<List<StudentByRoleData>> {
        return try {
            val response = apiErrorHandler.executeWithAuthHandling {
                userManagementService.getStudentsByRole()
            }
            response.fold(
                onSuccess = { studentsResponse ->
                    Result.success(studentsResponse.users)
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getParentsByRole(): Result<List<ParentByRoleData>> {
        return try {
            val response = apiErrorHandler.executeWithAuthHandling {
                userManagementService.getParentsByRole()
            }
            response.fold(
                onSuccess = { parentsResponse ->
                    Result.success(parentsResponse.users)
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            if (apiErrorHandler.handleAuthenticationException(e)) {
                Result.failure(Exception("Authentication failed: ${e.message}"))
            } else {
                Result.failure(e)
            }
        }
    }
    
    // Utility methods for UI
    fun getGradeDisplayName(grade: GradeEnum): String = grade.displayName
    
    fun getSexDisplayName(sex: SexEnum): String = sex.displayName
    
    fun getUserTypeDisplayName(userType: UserTypeEnum): String = userType.displayName
    
    fun getGradeOptions(): List<GradeEnum> = GradeEnum.values().toList()
    
    fun getSexOptions(): List<SexEnum> = SexEnum.values().toList()
    
    fun getParentTypeOptions(): List<UserTypeEnum> = listOf(
        UserTypeEnum.DAD,
        UserTypeEnum.MUM,
        UserTypeEnum.GRANDPARENTS
    )
}
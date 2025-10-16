package com.seecolab.istudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

// Backend API Enums
enum class SexEnum(val value: String) {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");
    
    val displayName: String
        get() = when (this) {
            MALE -> "男"
            FEMALE -> "女"
            OTHER -> "保密"
        }
}

enum class GradeEnum(val value: String) {
    GRADE_1("grade_1"),
    GRADE_2("grade_2"),
    GRADE_3("grade_3"),
    GRADE_4("grade_4"),
    GRADE_5("grade_5"),
    GRADE_6("grade_6"),
    GRADE_7("grade_7"),
    GRADE_8("grade_8"),
    GRADE_9("grade_9");
    
    val displayName: String
        get() = when (this) {
            GRADE_1 -> "一年级"
            GRADE_2 -> "二年级"
            GRADE_3 -> "三年级"
            GRADE_4 -> "四年级"
            GRADE_5 -> "五年级"
            GRADE_6 -> "六年级"
            GRADE_7 -> "七年级"
            GRADE_8 -> "八年级"
            GRADE_9 -> "九年级"
        }
}

enum class UserTypeEnum(val value: Int, val displayName: String) {
    DAD(0, "爸爸"),
    MUM(1, "妈妈"),
    GRANDPA(2, "爷爷"),
    GRANDMA(3, "奶奶");
    
    // For backward compatibility with old values
    companion object {
        val STUDENT = DAD // Map to DAD for compatibility
        val GRANDPARENTS = GRANDPA // Map to GRANDPA for compatibility
        val OTHERS = GRANDPA // Map to GRANDPA for compatibility
    }
}

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val phoneNumber: String,
    val role: UserRole,
    val isLoggedIn: Boolean = false,
    val lastLoginTime: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis()
)

enum class UserRole {
    STUDENT,
    PARENT
}

// Backend API Request/Response Models
data class RegistrationRequest(
    val student_username: String,
    val student_telephone: String,
    val student_age: Int,
    val student_sex: SexEnum,
    val student_grade: GradeEnum,
    val student_password: String,
    val parent_username: String,
    val parent_telephone: String,
    val parent_password: String,
    val parent_type: Int // Send integer value directly
)

data class LoginRequest(
    val username: String,
    val password: String,
    val user_type: String? = null // Optional - backend will determine user type
)

data class LogoutRequest(
    val user_id: String,
    val user_type: Int // 0=student, 1=dad, 2=mum, 3=grandparents
)

data class StudentCreate(
    val username: String,
    val telephone: String,
    val age: Int,
    val sex: SexEnum,
    val grade: GradeEnum,
    val password: String
)

data class StudentUpdate(
    val username: String? = null,
    val telephone: String? = null,
    val age: Int? = null,
    val sex: SexEnum? = null,
    val grade: GradeEnum? = null,
    val password: String? = null
)

data class ParentCreate(
    val username: String,
    val telephone: String,
    val password: String,
    val parent_type: Int, // Send integer value directly
    val is_master: Boolean = false
)

data class AddParentRequest(
    val parent_username: String, // Field name matches backend
    val parent_telephone: String, // Field name matches backend
    val parent_password: String, // Field name matches backend
    val parent_type: Int // Send integer value directly: 0=dad, 1=mum, 2=grandpa, 3=grandma
)

data class AddStudentRequest(
    val student_username: String, // Field name matches backend
    val student_telephone: String, // Field name matches backend
    val student_age: Int,
    val student_sex: SexEnum,
    val student_grade: GradeEnum,
    val student_password: String // Field name matches backend
)

// Response Models
data class RegistrationResponse(
    val student: StudentResponse,
    val parent: ParentResponse,
    val message: String
)

data class LoginResponse(
    val user_id: String,
    val username: String,
    val user_type: String, // "student" or "parent"
    val access_token: String,
    val token_type: String = "bearer",
    val expires_in: Int, // Token expiration time in seconds
    val message: String
)

data class StudentResponse(
    val user_id: String, // UUID as string
    val username: String,
    val telephone: String,
    val age: Int,
    val sex: String, // Chinese value from backend
    val grade: String, // Chinese value from backend
    val reg_date: String, // ISO 8601 datetime string
    val master_user_id: String, // UUID as string
    val deleted: Boolean = false
)

data class ParentResponse(
    val user_id: String, // UUID as string
    val username: String,
    val telephone: String,
    val user_type: String, // Chinese value from backend
    val master_user_id: String, // UUID as string
    val is_master: Boolean, // Whether this parent is the master account
    val deleted: Boolean = false
)

data class UserResponse(
    val user_id: String,
    val username: String,
    val telephone: String,
    val user_type: UserTypeEnum,
    val created_at: String,
    val updated_at: String
)

data class StudentWorkListItem(
    val work_id: String,
    val student_id: String,
    val create_time: String,
    val paper_name: String?,
    val subject: String?,
    val score: Float?,
    val oss_id: String? = null,
    val status: String? = null, // e.g., "completed", "pending", etc.
    val wrong_count: Int? = null
)

data class StudentWorkListResponse(
    val success: Boolean = true,
    val total_count: Int,
    val page: Int,
    val page_size: Int,
    val total_pages: Int,
    val works: List<StudentWorkListItem>,
    val student_id: String,
    val start_date: String?,
    val end_date: String?,
    val message: String
)

data class QuestionAnswer(
    val question: String,
    val student_answer: String,
    val correct_answer: String,
    val description: String
)

data class StudentWorkResponse(
    val id: String, // UUID
    val student_id: String, // UUID
    val action_date: String, // ISO 8601 with CST timezone
    val paper: String, // file URL
    val paper_name: String?, // paper/exam name
    val subject: String?, // subject name
    val student_name: String?, // student name
    val username: String?, // student username
    val oss_id: String?, // filename
    val score: Float?,
    val right_answer: List<QuestionAnswer>?,
    val wrong_answer: List<QuestionAnswer>?,
    val suggestion: String?,
    val exam_id: String? // UUID
)

// 错题详情响应
data class WrongDetailResponse(
    val work_id: String,
    val student_id: String,
    val subject: String?,
    val paper_name: String?,
    val wrong_answer: List<QuestionAnswer>
)

data class LogResponse(
    val id: String,
    val user_id: String,
    val user_type: String, // Chinese value
    val action: String,
    val action_date: String, // ISO 8601 with CST timezone
    val details: String?,
    val ip_address: String?
)

data class FileUploadResponse(
    val success: Boolean,
    val file_url: String?,
    val object_key: String?,
    val message: String
)

data class RelatedUsersResponse(
    val master_parent: ParentResponse,
    val students: List<StudentResponse>,
    val other_parents: List<ParentResponse>,
    val message: String
)

// New response models for role-specific user queries
data class UsersByRoleResponse(
    val role_type: String, // "student" or "parent"
    val count: Int,
    val users: List<Any>, // Will be parsed based on role_type
    val message: String
)

// Specific response models for students and parents by role
data class StudentsByRoleResponse(
    val role_type: String, // "student"
    val count: Int,
    val users: List<StudentByRoleData>,
    val message: String
)

data class ParentsByRoleResponse(
    val role_type: String, // "parent"
    val count: Int,
    val users: List<ParentByRoleData>,
    val message: String
)

data class StudentByRoleData(
    val user_id: String,
    val username: String,
    val telephone: String,
    val age: Int,
    val sex: String,
    val grade: String,
    val reg_date: String,
    val master_user_id: String,
    val deleted: Boolean
) {
    val is_master: Boolean = false // Students are never master users
}

data class ParentByRoleData(
    val user_id: String,
    val username: String,
    val telephone: String,
    val user_type: String,
    val master_user_id: String,
    val is_master: Boolean,
    val deleted: Boolean
)

// File upload models
data class PaperWorkSubmission(
    val student_id: String, // Student User ID (UUID)
    val file_url: String, // URL of the uploaded paper file
    val object_key: String, // OSS object key for the file
    val score: Float? = null, // AI-provided score (0-100)
    val right_answer: String? = null, // Correct answer (max 1000 chars)
    val wrong_answer: String? = null, // Wrong answer (max 1000 chars) 
    val suggestion: String? = null, // AI suggestion (max 2000 chars)
    val exam_id: String? = null // Exam ID (UUID)
)

data class FileAccessRequest(
    val object_key: String, // OSS object key
    val expires_in_seconds: Int = 3600 // URL expiration time in seconds
)

// Legacy models for backward compatibility
data class VerificationCodeRequest(
    val phoneNumber: String
)

data class SignupRequest(
    val phoneNumber: String,
    val password: String,
    val role: UserRole,
    val verificationCode: String
)

data class SignupResponse(
    val success: Boolean,
    val message: String,
    val user: User? = null,
    val token: String? = null
)

enum class LoginType {
    PASSWORD,
    VERIFICATION_CODE
}

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)

// Token verification response model
data class TokenVerifyResponse(
    val valid: Boolean,
    val expired: Boolean,
    val user_id: String?,
    val username: String?,
    val user_type: Int?,
    val user_role: String?,
    val message: String
)

// Streaming upload models
data class UploadStreamEvent(
    val type: String, // "content", "result", "complete", "error"
    val content: String? = null, // For type "content"
    val data: StudentWorkResponse? = null, // For type "result"
    val message: String? = null // For type "error"
)

data class UploadProgressState(
    val isUploading: Boolean = false,
    val aiContent: String = "",
    val streamingContent: String = "", // Real-time streaming content display
    val isStreaming: Boolean = false, // Whether content is currently streaming
    val result: StudentWorkResponse? = null,
    val error: String? = null,
    val isComplete: Boolean = false
)

// Delete student work response model
data class DeleteStudentWorkResponse(
    val success: Boolean,
    val message: String,
    val deleted_work_id: String,
    val student_id: String
)
package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.local.dao.TeacherDao
import com.seecolab.istudy.data.local.dao.TeacherBookingDao
import com.seecolab.istudy.data.model.*
import com.seecolab.istudy.data.api.TeacherService
import com.seecolab.istudy.data.api.TeacherSearchRequest
import com.seecolab.istudy.data.api.TeacherRemote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeacherRepository @Inject constructor(
    private val teacherDao: TeacherDao,
    private val bookingDao: TeacherBookingDao,
    private val teacherService: TeacherService
) {
    suspend fun getTeacherById(id: Long): Teacher? {
        return teacherDao.getTeacherById(id)
    }

    fun getAvailableTeachers(): Flow<List<Teacher>> {
        return teacherDao.getAvailableTeachers()
    }

    fun getTeachersByLocation(location: String): Flow<List<Teacher>> {
        return teacherDao.getTeachersByLocation(location)
    }

    suspend fun insertTeacher(teacher: Teacher): Long {
        return teacherDao.insertTeacher(teacher)
    }

    suspend fun insertTeachers(teachers: List<Teacher>) {
        teacherDao.insertTeachers(teachers)
    }

    suspend fun updateTeacher(teacher: Teacher) {
        teacherDao.updateTeacher(teacher)
    }

    suspend fun deleteTeacher(teacher: Teacher) {
        teacherDao.deleteTeacher(teacher)
    }

    // Booking methods
    suspend fun getBookingById(id: Long): TeacherBooking? {
        return bookingDao.getBookingById(id)
    }

    fun getBookingsByStudent(studentId: Long): Flow<List<TeacherBooking>> {
        return bookingDao.getBookingsByStudent(studentId)
    }

    fun getBookingsByTeacher(teacherId: Long): Flow<List<TeacherBooking>> {
        return bookingDao.getBookingsByTeacher(teacherId)
    }

    suspend fun insertBooking(booking: TeacherBooking): Long {
        return bookingDao.insertBooking(booking)
    }

    suspend fun updateBooking(booking: TeacherBooking) {
        bookingDao.updateBooking(booking)
    }

    suspend fun deleteBooking(booking: TeacherBooking) {
        bookingDao.deleteBooking(booking)
    }

    fun getFilteredTeachers(
        location: String? = null,
        subjects: List<Subject>? = null,
        gender: Gender? = null
    ): Flow<List<Teacher>> {
        // 本地过滤保留给离线/占位使用
        return if (location != null) {
            getTeachersByLocation(location)
        } else {
            getAvailableTeachers()
        }
    }

    // 新增：服务端按条件查询教师列表
    suspend fun searchTeachers(
        sex: String? = null,
        subject: String? = null,
        region: String? = null,
        page: Int = 1,
        pageSize: Int = 20
    ): List<Teacher> {
        val req = TeacherSearchRequest(
            sex = sex,
            subject = subject,
            region = region,
            page = page,
            page_size = pageSize
        )
        val resp = teacherService.searchTeachers(req)
        if (resp.isSuccessful) {
            val body = resp.body()
            val remotes = body?.teachers ?: emptyList()
            // Map TeacherRemote -> domain Teacher (provide safe defaults)
            return remotes.map { r ->
                // 计算年龄（生日格式：yyyy-MM-dd'T'HH:mm:ss）
                val age = runCatching {
                    val fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    val birthStr = r.birthday ?: return@runCatching 0
                    val birth = java.time.LocalDate.parse(birthStr, fmt)
                    java.time.Period.between(birth, java.time.LocalDate.now()).years
                }.getOrElse { 0 }

                // 科目枚举映射
                val mappedSubjects = r.subject.mapNotNull { s ->
                    when (s) {
                        "语文" -> Subject.CHINESE
                        "数学" -> Subject.MATH
                        "英语" -> Subject.ENGLISH
                        "物理" -> Subject.PHYSICS
                        "化学" -> Subject.CHEMISTRY
                        "生物" -> Subject.BIOLOGY
                        "历史" -> Subject.HISTORY
                        "政治" -> Subject.POLITICS
                        "地理" -> Subject.GEOGRAPHY
                        else -> null
                    }
                }

                // 地区 + 年级展示
                val displayLocation = buildString {
                    append(r.address ?: "")
                    val grades = r.grade ?: emptyList()
                    if (grades.isNotEmpty()) {
                        val gradeLabel = grades.joinToString("、") { g ->
                            when (g) {
                                "grade_1" -> "一年级"
                                "grade_2" -> "二年级"
                                "grade_3" -> "三年级"
                                "grade_4" -> "四年级"
                                "grade_5" -> "五年级"
                                "grade_6" -> "六年级"
                                "grade_7" -> "七年级"
                                "grade_8" -> "八年级"
                                "grade_9" -> "九年级"
                                else -> g
                            }
                        }
                        if (isNotEmpty()) append(" | ")
                        append("年级: ")
                        append(gradeLabel)
                    }
                }

                // 映射为域模型（使用命名参数，避免顺序错误）
                Teacher(
                    id = 0L,
                    name = r.real_name.ifBlank { r.user_name },
                    gender = when (r.sex?.lowercase()) {
                        "male", "m", "男" -> Gender.MALE
                        "female", "f", "女" -> Gender.FEMALE
                        else -> Gender.UNKNOWN
                    },
                    age = age,
                    subjects = mappedSubjects,
                    location = displayLocation,
                    experience = 0,
                    rating = 0.0f,
                    hourlyRate = 0.0,
                    description = r.user_id ?: "",
                    avatar = null,
                    isAvailable = true,
                    qualifications = emptyList()
                )
            }
        } else {
            throw Exception("Teacher search failed: HTTP ${resp.code()}")
        }
    }
}
package com.seecolab.istudy.data.local.dao

import androidx.room.*
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Long): Student?

    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM students ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCurrentStudent(): Student?
}

@Dao
interface TeacherDao {
    @Query("SELECT * FROM teachers WHERE id = :id")
    suspend fun getTeacherById(id: Long): Teacher?

    @Query("SELECT * FROM teachers WHERE isAvailable = 1")
    fun getAvailableTeachers(): Flow<List<Teacher>>

    @Query("SELECT * FROM teachers WHERE location LIKE '%' || :location || '%' AND isAvailable = 1")
    fun getTeachersByLocation(location: String): Flow<List<Teacher>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacher(teacher: Teacher): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeachers(teachers: List<Teacher>)

    @Update
    suspend fun updateTeacher(teacher: Teacher)

    @Delete
    suspend fun deleteTeacher(teacher: Teacher)
}

@Dao
interface TeacherBookingDao {
    @Query("SELECT * FROM teacher_bookings WHERE id = :id")
    suspend fun getBookingById(id: Long): TeacherBooking?

    @Query("SELECT * FROM teacher_bookings WHERE studentId = :studentId ORDER BY scheduledTime DESC")
    fun getBookingsByStudent(studentId: Long): Flow<List<TeacherBooking>>

    @Query("SELECT * FROM teacher_bookings WHERE teacherId = :teacherId ORDER BY scheduledTime DESC")
    fun getBookingsByTeacher(teacherId: Long): Flow<List<TeacherBooking>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: TeacherBooking): Long

    @Update
    suspend fun updateBooking(booking: TeacherBooking)

    @Delete
    suspend fun deleteBooking(booking: TeacherBooking)
}
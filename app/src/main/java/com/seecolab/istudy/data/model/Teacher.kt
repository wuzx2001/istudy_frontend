package com.seecolab.istudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "teachers")
@Parcelize
data class Teacher(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val gender: Gender,
    val age: Int,
    val subjects: List<Subject>,
    val location: String,
    val experience: Int, // years of experience
    val rating: Float = 0f,
    val hourlyRate: Double,
    val description: String,
    val avatar: String? = null,
    val isAvailable: Boolean = true,
    val qualifications: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Entity(tableName = "teacher_bookings")
@Parcelize
data class TeacherBooking(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val studentId: Long,
    val teacherId: Long,
    val subject: Subject,
    val scheduledTime: Long,
    val duration: Int, // in minutes
    val status: BookingStatus,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class BookingStatus {
    PENDING, CONFIRMED, COMPLETED, CANCELLED
}
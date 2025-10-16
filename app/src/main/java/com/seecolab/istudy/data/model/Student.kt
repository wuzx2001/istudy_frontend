package com.seecolab.istudy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "students")
@Parcelize
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val age: Int,
    val gender: Gender,
    val grade: Grade,
    val subjectPreferences: List<Subject> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable

enum class Gender {
    MALE, FEMALE, UNKNOWN
}

enum class Grade(val displayName: String, val value: Int) {
    GRADE_1("一年级", 1),
    GRADE_2("二年级", 2),
    GRADE_3("三年级", 3),
    GRADE_4("四年级", 4),
    GRADE_5("五年级", 5),
    GRADE_6("六年级", 6),
    GRADE_7("初一", 7),
    GRADE_8("初二", 8),
    GRADE_9("初三", 9),
    GRADE_10("高一", 10),
    GRADE_11("高二", 11),
    GRADE_12("高三", 12)
}

enum class Subject(val displayName: String) {
    CHINESE("语文"),
    MATH("数学"),
    ENGLISH("英语"),
    PHYSICS("物理"),
    CHEMISTRY("化学"),
    BIOLOGY("生物"),
    HISTORY("历史"),
    GEOGRAPHY("地理"),
    POLITICS("政治")
}
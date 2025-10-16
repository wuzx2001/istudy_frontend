package com.seecolab.istudy.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.seecolab.istudy.data.local.converter.TypeConverters as AppTypeConverters
import com.seecolab.istudy.data.local.dao.*
import com.seecolab.istudy.data.model.*

@Database(
    entities = [
        Student::class,
        Course::class,
        CourseProgress::class,
        Question::class,
        TestPaper::class,
        TestSubmission::class,
        HomeworkSubmission::class,
        User::class,
        LearningReport::class,
        StudySession::class,
        Teacher::class,
        TeacherBooking::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(AppTypeConverters::class)
abstract class StudyDatabase : RoomDatabase() {
    
    abstract fun studentDao(): StudentDao
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun courseProgressDao(): CourseProgressDao
    abstract fun questionDao(): QuestionDao
    abstract fun testPaperDao(): TestPaperDao
    abstract fun testSubmissionDao(): TestSubmissionDao
    abstract fun homeworkDao(): HomeworkDao
    abstract fun learningReportDao(): LearningReportDao
    abstract fun studySessionDao(): StudySessionDao
    abstract fun teacherDao(): TeacherDao
    abstract fun teacherBookingDao(): TeacherBookingDao

    companion object {
        @Volatile
        private var INSTANCE: StudyDatabase? = null

        fun getDatabase(context: Context): StudyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyDatabase::class.java,
                    "study_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.seecolab.istudy.di

import android.content.Context
import androidx.room.Room
import com.seecolab.istudy.data.local.StudyDatabase
import com.seecolab.istudy.data.local.TokenManager
import com.seecolab.istudy.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStudyDatabase(@ApplicationContext context: Context): StudyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StudyDatabase::class.java,
            "study_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(context)
    }

    @Provides
    fun provideStudentDao(database: StudyDatabase): StudentDao = database.studentDao()

    @Provides
    fun provideUserDao(database: StudyDatabase): UserDao = database.userDao()

    @Provides
    fun provideCourseDao(database: StudyDatabase): CourseDao = database.courseDao()

    @Provides
    fun provideCourseProgressDao(database: StudyDatabase): CourseProgressDao = database.courseProgressDao()

    @Provides
    fun provideQuestionDao(database: StudyDatabase): QuestionDao = database.questionDao()

    @Provides
    fun provideTestPaperDao(database: StudyDatabase): TestPaperDao = database.testPaperDao()

    @Provides
    fun provideTestSubmissionDao(database: StudyDatabase): TestSubmissionDao = database.testSubmissionDao()

    @Provides
    fun provideHomeworkDao(database: StudyDatabase): HomeworkDao = database.homeworkDao()

    @Provides
    fun provideLearningReportDao(database: StudyDatabase): LearningReportDao = database.learningReportDao()

    @Provides
    fun provideStudySessionDao(database: StudyDatabase): StudySessionDao = database.studySessionDao()

    @Provides
    fun provideTeacherDao(database: StudyDatabase): TeacherDao = database.teacherDao()

    @Provides
    fun provideTeacherBookingDao(database: StudyDatabase): TeacherBookingDao = database.teacherBookingDao()
}
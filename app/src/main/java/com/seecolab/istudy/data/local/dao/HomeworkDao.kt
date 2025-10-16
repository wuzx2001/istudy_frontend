package com.seecolab.istudy.data.local.dao

import androidx.room.*
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {
    @Query("SELECT * FROM homework_submissions WHERE id = :id")
    suspend fun getHomeworkById(id: Long): HomeworkSubmission?

    @Query("SELECT * FROM homework_submissions WHERE studentId = :studentId ORDER BY submittedAt DESC")
    fun getHomeworkByStudent(studentId: Long): Flow<List<HomeworkSubmission>>

    @Query("SELECT * FROM homework_submissions WHERE status = :status")
    fun getHomeworkByStatus(status: SubmissionStatus): Flow<List<HomeworkSubmission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomework(homework: HomeworkSubmission): Long

    @Update
    suspend fun updateHomework(homework: HomeworkSubmission)

    @Delete
    suspend fun deleteHomework(homework: HomeworkSubmission)
}

@Dao
interface LearningReportDao {
    @Query("SELECT * FROM learning_reports WHERE id = :id")
    suspend fun getReportById(id: Long): LearningReport?

    @Query("SELECT * FROM learning_reports WHERE studentId = :studentId ORDER BY generatedAt DESC")
    fun getReportsByStudent(studentId: Long): Flow<List<LearningReport>>

    @Query("SELECT * FROM learning_reports WHERE studentId = :studentId ORDER BY generatedAt DESC LIMIT 1")
    suspend fun getLatestReport(studentId: Long): LearningReport?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: LearningReport): Long

    @Update
    suspend fun updateReport(report: LearningReport)

    @Delete
    suspend fun deleteReport(report: LearningReport)
}

@Dao
interface StudySessionDao {
    @Query("SELECT * FROM study_sessions WHERE id = :id")
    suspend fun getSessionById(id: Long): StudySession?

    @Query("SELECT * FROM study_sessions WHERE studentId = :studentId ORDER BY startedAt DESC")
    fun getSessionsByStudent(studentId: Long): Flow<List<StudySession>>

    @Query("SELECT * FROM study_sessions WHERE studentId = :studentId AND subject = :subject ORDER BY startedAt DESC")
    fun getSessionsByStudentAndSubject(studentId: Long, subject: Subject): Flow<List<StudySession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: StudySession): Long

    @Update
    suspend fun updateSession(session: StudySession)

    @Delete
    suspend fun deleteSession(session: StudySession)
}
package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.local.dao.HomeworkDao
import com.seecolab.istudy.data.local.dao.LearningReportDao
import com.seecolab.istudy.data.local.dao.StudySessionDao
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeworkRepository @Inject constructor(
    private val homeworkDao: HomeworkDao,
    private val reportDao: LearningReportDao,
    private val sessionDao: StudySessionDao
) {
    // Homework methods
    suspend fun getHomeworkById(id: Long): HomeworkSubmission? {
        return homeworkDao.getHomeworkById(id)
    }

    fun getHomeworkByStudent(studentId: Long): Flow<List<HomeworkSubmission>> {
        return homeworkDao.getHomeworkByStudent(studentId)
    }

    fun getHomeworkByStatus(status: SubmissionStatus): Flow<List<HomeworkSubmission>> {
        return homeworkDao.getHomeworkByStatus(status)
    }

    suspend fun insertHomework(homework: HomeworkSubmission): Long {
        return homeworkDao.insertHomework(homework)
    }

    suspend fun updateHomework(homework: HomeworkSubmission) {
        homeworkDao.updateHomework(homework)
    }

    suspend fun deleteHomework(homework: HomeworkSubmission) {
        homeworkDao.deleteHomework(homework)
    }

    // Learning Report methods
    suspend fun getReportById(id: Long): LearningReport? {
        return reportDao.getReportById(id)
    }

    fun getReportsByStudent(studentId: Long): Flow<List<LearningReport>> {
        return reportDao.getReportsByStudent(studentId)
    }

    suspend fun getLatestReport(studentId: Long): LearningReport? {
        return reportDao.getLatestReport(studentId)
    }

    suspend fun insertReport(report: LearningReport): Long {
        return reportDao.insertReport(report)
    }

    suspend fun updateReport(report: LearningReport) {
        reportDao.updateReport(report)
    }

    suspend fun deleteReport(report: LearningReport) {
        reportDao.deleteReport(report)
    }

    // Study Session methods
    suspend fun getSessionById(id: Long): StudySession? {
        return sessionDao.getSessionById(id)
    }

    fun getSessionsByStudent(studentId: Long): Flow<List<StudySession>> {
        return sessionDao.getSessionsByStudent(studentId)
    }

    fun getSessionsByStudentAndSubject(studentId: Long, subject: Subject): Flow<List<StudySession>> {
        return sessionDao.getSessionsByStudentAndSubject(studentId, subject)
    }

    suspend fun insertSession(session: StudySession): Long {
        return sessionDao.insertSession(session)
    }

    suspend fun updateSession(session: StudySession) {
        sessionDao.updateSession(session)
    }

    suspend fun deleteSession(session: StudySession) {
        sessionDao.deleteSession(session)
    }

    suspend fun submitHomeworkForAnalysis(
        studentId: Long,
        subject: Subject,
        imagePath: String
    ): Long {
        val homework = HomeworkSubmission(
            studentId = studentId,
            subject = subject,
            imagePath = imagePath,
            status = SubmissionStatus.PENDING
        )
        return insertHomework(homework)
    }

    suspend fun updateHomeworkWithAnalysis(
        homeworkId: Long,
        analysisResult: AnalysisResult,
        recommendations: List<String>,
        testPaperId: Long?
    ) {
        val homework = getHomeworkById(homeworkId)
        homework?.let {
            val updated = it.copy(
                analysisResult = analysisResult,
                aiRecommendations = recommendations,
                generatedTestPaperId = testPaperId,
                status = SubmissionStatus.COMPLETED,
                analyzedAt = System.currentTimeMillis()
            )
            updateHomework(updated)
        }
    }
}
package com.seecolab.istudy.data.repository

import com.seecolab.istudy.data.local.dao.QuestionDao
import com.seecolab.istudy.data.local.dao.TestPaperDao
import com.seecolab.istudy.data.local.dao.TestSubmissionDao
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    private val questionDao: QuestionDao,
    private val testPaperDao: TestPaperDao,
    private val submissionDao: TestSubmissionDao
) {
    // Question methods
    suspend fun getQuestionById(id: Long): Question? {
        return questionDao.getQuestionById(id)
    }

    fun getQuestionsBySubjectAndGrade(subject: Subject, grade: Grade): Flow<List<Question>> {
        return questionDao.getQuestionsBySubjectAndGrade(subject, grade)
    }

    fun getQuestionsByDifficulty(subject: Subject, grade: Grade, difficulty: Difficulty): Flow<List<Question>> {
        return questionDao.getQuestionsByDifficulty(subject, grade, difficulty)
    }

    suspend fun getQuestionsByIds(questionIds: List<Long>): List<Question> {
        return questionDao.getQuestionsByIds(questionIds)
    }

    suspend fun insertQuestion(question: Question): Long {
        return questionDao.insertQuestion(question)
    }

    suspend fun insertQuestions(questions: List<Question>) {
        questionDao.insertQuestions(questions)
    }

    suspend fun updateQuestion(question: Question) {
        questionDao.updateQuestion(question)
    }

    suspend fun deleteQuestion(question: Question) {
        questionDao.deleteQuestion(question)
    }

    // Test Paper methods
    suspend fun getTestPaperById(id: Long): TestPaper? {
        return testPaperDao.getTestPaperById(id)
    }

    fun getTestPapersBySubjectAndGrade(subject: Subject, grade: Grade): Flow<List<TestPaper>> {
        return testPaperDao.getTestPapersBySubjectAndGrade(subject, grade)
    }

    fun getTestPapersByExamType(examType: ExamType): Flow<List<TestPaper>> {
        return testPaperDao.getTestPapersByExamType(examType)
    }

    suspend fun insertTestPaper(testPaper: TestPaper): Long {
        return testPaperDao.insertTestPaper(testPaper)
    }

    suspend fun updateTestPaper(testPaper: TestPaper) {
        testPaperDao.updateTestPaper(testPaper)
    }

    suspend fun deleteTestPaper(testPaper: TestPaper) {
        testPaperDao.deleteTestPaper(testPaper)
    }

    // Test Submission methods
    suspend fun getSubmissionById(id: Long): TestSubmission? {
        return submissionDao.getSubmissionById(id)
    }

    fun getSubmissionsByStudent(studentId: Long): Flow<List<TestSubmission>> {
        return submissionDao.getSubmissionsByStudent(studentId)
    }

    fun getSubmissionsByTestPaper(testPaperId: Long): Flow<List<TestSubmission>> {
        return submissionDao.getSubmissionsByTestPaper(testPaperId)
    }

    suspend fun insertSubmission(submission: TestSubmission): Long {
        return submissionDao.insertSubmission(submission)
    }

    suspend fun updateSubmission(submission: TestSubmission) {
        submissionDao.updateSubmission(submission)
    }

    suspend fun deleteSubmission(submission: TestSubmission) {
        submissionDao.deleteSubmission(submission)
    }

    suspend fun generateTestPaper(
        subject: Subject,
        grade: Grade,
        examType: ExamType,
        questionCount: Int = 20,
        difficulty: Difficulty? = null
    ): TestPaper? {
        val questions = if (difficulty != null) {
            getQuestionsByDifficulty(subject, grade, difficulty)
        } else {
            getQuestionsBySubjectAndGrade(subject, grade)
        }
        
        // This is a simplified implementation
        // In a real app, you'd implement more sophisticated question selection logic
        return null // Would return generated test paper
    }
}
package com.seecolab.istudy.data.local.dao

import androidx.room.*
import com.seecolab.istudy.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getQuestionById(id: Long): Question?

    @Query("SELECT * FROM questions WHERE subject = :subject AND grade = :grade")
    fun getQuestionsBySubjectAndGrade(subject: Subject, grade: Grade): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE subject = :subject AND grade = :grade AND difficulty = :difficulty")
    fun getQuestionsByDifficulty(subject: Subject, grade: Grade, difficulty: Difficulty): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE id IN (:questionIds)")
    suspend fun getQuestionsByIds(questionIds: List<Long>): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)
}

@Dao
interface TestPaperDao {
    @Query("SELECT * FROM test_papers WHERE id = :id")
    suspend fun getTestPaperById(id: Long): TestPaper?

    @Query("SELECT * FROM test_papers WHERE subject = :subject AND grade = :grade")
    fun getTestPapersBySubjectAndGrade(subject: Subject, grade: Grade): Flow<List<TestPaper>>

    @Query("SELECT * FROM test_papers WHERE examType = :examType")
    fun getTestPapersByExamType(examType: ExamType): Flow<List<TestPaper>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestPaper(testPaper: TestPaper): Long

    @Update
    suspend fun updateTestPaper(testPaper: TestPaper)

    @Delete
    suspend fun deleteTestPaper(testPaper: TestPaper)
}

@Dao
interface TestSubmissionDao {
    @Query("SELECT * FROM test_submissions WHERE id = :id")
    suspend fun getSubmissionById(id: Long): TestSubmission?

    @Query("SELECT * FROM test_submissions WHERE studentId = :studentId ORDER BY submittedAt DESC")
    fun getSubmissionsByStudent(studentId: Long): Flow<List<TestSubmission>>

    @Query("SELECT * FROM test_submissions WHERE testPaperId = :testPaperId ORDER BY submittedAt DESC")
    fun getSubmissionsByTestPaper(testPaperId: Long): Flow<List<TestSubmission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: TestSubmission): Long

    @Update
    suspend fun updateSubmission(submission: TestSubmission)

    @Delete
    suspend fun deleteSubmission(submission: TestSubmission)
}
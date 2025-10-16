package com.seecolab.istudy.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.seecolab.istudy.data.local.dao.CourseDao;
import com.seecolab.istudy.data.local.dao.CourseDao_Impl;
import com.seecolab.istudy.data.local.dao.CourseProgressDao;
import com.seecolab.istudy.data.local.dao.CourseProgressDao_Impl;
import com.seecolab.istudy.data.local.dao.HomeworkDao;
import com.seecolab.istudy.data.local.dao.HomeworkDao_Impl;
import com.seecolab.istudy.data.local.dao.LearningReportDao;
import com.seecolab.istudy.data.local.dao.LearningReportDao_Impl;
import com.seecolab.istudy.data.local.dao.QuestionDao;
import com.seecolab.istudy.data.local.dao.QuestionDao_Impl;
import com.seecolab.istudy.data.local.dao.StudentDao;
import com.seecolab.istudy.data.local.dao.StudentDao_Impl;
import com.seecolab.istudy.data.local.dao.StudySessionDao;
import com.seecolab.istudy.data.local.dao.StudySessionDao_Impl;
import com.seecolab.istudy.data.local.dao.TeacherBookingDao;
import com.seecolab.istudy.data.local.dao.TeacherBookingDao_Impl;
import com.seecolab.istudy.data.local.dao.TeacherDao;
import com.seecolab.istudy.data.local.dao.TeacherDao_Impl;
import com.seecolab.istudy.data.local.dao.TestPaperDao;
import com.seecolab.istudy.data.local.dao.TestPaperDao_Impl;
import com.seecolab.istudy.data.local.dao.TestSubmissionDao;
import com.seecolab.istudy.data.local.dao.TestSubmissionDao_Impl;
import com.seecolab.istudy.data.local.dao.UserDao;
import com.seecolab.istudy.data.local.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudyDatabase_Impl extends StudyDatabase {
  private volatile StudentDao _studentDao;

  private volatile UserDao _userDao;

  private volatile CourseDao _courseDao;

  private volatile CourseProgressDao _courseProgressDao;

  private volatile QuestionDao _questionDao;

  private volatile TestPaperDao _testPaperDao;

  private volatile TestSubmissionDao _testSubmissionDao;

  private volatile HomeworkDao _homeworkDao;

  private volatile LearningReportDao _learningReportDao;

  private volatile StudySessionDao _studySessionDao;

  private volatile TeacherDao _teacherDao;

  private volatile TeacherBookingDao _teacherBookingDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `students` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `age` INTEGER NOT NULL, `gender` TEXT NOT NULL, `grade` TEXT NOT NULL, `subjectPreferences` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `courses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subject` TEXT NOT NULL, `grade` TEXT NOT NULL, `chapterName` TEXT NOT NULL, `description` TEXT NOT NULL, `keyPoints` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `estimatedStudyTime` INTEGER NOT NULL, `prerequisites` TEXT NOT NULL, `learningObjectives` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `course_progress` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `courseId` INTEGER NOT NULL, `progress` REAL NOT NULL, `completedKeyPoints` TEXT NOT NULL, `timeSpent` INTEGER NOT NULL, `lastAccessedAt` INTEGER NOT NULL, `startedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `questions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subject` TEXT NOT NULL, `grade` TEXT NOT NULL, `chapter` TEXT NOT NULL, `questionText` TEXT NOT NULL, `questionType` TEXT NOT NULL, `options` TEXT NOT NULL, `correctAnswer` TEXT NOT NULL, `explanation` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `points` INTEGER NOT NULL, `tags` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `test_papers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `subject` TEXT NOT NULL, `grade` TEXT NOT NULL, `examType` TEXT NOT NULL, `questionIds` TEXT NOT NULL, `totalPoints` INTEGER NOT NULL, `timeLimit` INTEGER NOT NULL, `description` TEXT, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `test_submissions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `testPaperId` INTEGER NOT NULL, `answers` TEXT NOT NULL, `score` INTEGER NOT NULL, `totalPoints` INTEGER NOT NULL, `timeSpent` INTEGER NOT NULL, `submittedAt` INTEGER NOT NULL, `startedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `homework_submissions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `subject` TEXT NOT NULL, `imagePath` TEXT NOT NULL, `analysisResult` TEXT, `aiRecommendations` TEXT NOT NULL, `generatedTestPaperId` INTEGER, `status` TEXT NOT NULL, `submittedAt` INTEGER NOT NULL, `analyzedAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`phoneNumber` TEXT NOT NULL, `role` TEXT NOT NULL, `isLoggedIn` INTEGER NOT NULL, `lastLoginTime` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`phoneNumber`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `learning_reports` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `reportPeriodStart` INTEGER NOT NULL, `reportPeriodEnd` INTEGER NOT NULL, `overallProgress` REAL NOT NULL, `subjectScores` TEXT NOT NULL, `completedTests` INTEGER NOT NULL, `totalStudyTime` INTEGER NOT NULL, `strengthAreas` TEXT NOT NULL, `improvementAreas` TEXT NOT NULL, `recommendations` TEXT NOT NULL, `generatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `study_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `subject` TEXT NOT NULL, `activityType` TEXT NOT NULL, `duration` INTEGER NOT NULL, `score` REAL, `completionRate` REAL NOT NULL, `startedAt` INTEGER NOT NULL, `completedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `teachers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `gender` TEXT NOT NULL, `age` INTEGER NOT NULL, `subjects` TEXT NOT NULL, `location` TEXT NOT NULL, `experience` INTEGER NOT NULL, `rating` REAL NOT NULL, `hourlyRate` REAL NOT NULL, `description` TEXT NOT NULL, `avatar` TEXT, `isAvailable` INTEGER NOT NULL, `qualifications` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `teacher_bookings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `studentId` INTEGER NOT NULL, `teacherId` INTEGER NOT NULL, `subject` TEXT NOT NULL, `scheduledTime` INTEGER NOT NULL, `duration` INTEGER NOT NULL, `status` TEXT NOT NULL, `notes` TEXT, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'aebdbd921ae060faea2e9ff59063efbc')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `students`");
        db.execSQL("DROP TABLE IF EXISTS `courses`");
        db.execSQL("DROP TABLE IF EXISTS `course_progress`");
        db.execSQL("DROP TABLE IF EXISTS `questions`");
        db.execSQL("DROP TABLE IF EXISTS `test_papers`");
        db.execSQL("DROP TABLE IF EXISTS `test_submissions`");
        db.execSQL("DROP TABLE IF EXISTS `homework_submissions`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `learning_reports`");
        db.execSQL("DROP TABLE IF EXISTS `study_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `teachers`");
        db.execSQL("DROP TABLE IF EXISTS `teacher_bookings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsStudents = new HashMap<String, TableInfo.Column>(8);
        _columnsStudents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("grade", new TableInfo.Column("grade", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("subjectPreferences", new TableInfo.Column("subjectPreferences", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudents.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudents = new TableInfo("students", _columnsStudents, _foreignKeysStudents, _indicesStudents);
        final TableInfo _existingStudents = TableInfo.read(db, "students");
        if (!_infoStudents.equals(_existingStudents)) {
          return new RoomOpenHelper.ValidationResult(false, "students(com.seecolab.istudy.data.model.Student).\n"
                  + " Expected:\n" + _infoStudents + "\n"
                  + " Found:\n" + _existingStudents);
        }
        final HashMap<String, TableInfo.Column> _columnsCourses = new HashMap<String, TableInfo.Column>(11);
        _columnsCourses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("grade", new TableInfo.Column("grade", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("chapterName", new TableInfo.Column("chapterName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("keyPoints", new TableInfo.Column("keyPoints", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("estimatedStudyTime", new TableInfo.Column("estimatedStudyTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("prerequisites", new TableInfo.Column("prerequisites", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("learningObjectives", new TableInfo.Column("learningObjectives", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourses = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourses = new TableInfo("courses", _columnsCourses, _foreignKeysCourses, _indicesCourses);
        final TableInfo _existingCourses = TableInfo.read(db, "courses");
        if (!_infoCourses.equals(_existingCourses)) {
          return new RoomOpenHelper.ValidationResult(false, "courses(com.seecolab.istudy.data.model.Course).\n"
                  + " Expected:\n" + _infoCourses + "\n"
                  + " Found:\n" + _existingCourses);
        }
        final HashMap<String, TableInfo.Column> _columnsCourseProgress = new HashMap<String, TableInfo.Column>(8);
        _columnsCourseProgress.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseProgress.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseProgress.put("courseId", new TableInfo.Column("courseId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseProgress.put("progress", new TableInfo.Column("progress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseProgress.put("completedKeyPoints", new TableInfo.Column("completedKeyPoints", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseProgress.put("timeSpent", new TableInfo.Column("timeSpent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseProgress.put("lastAccessedAt", new TableInfo.Column("lastAccessedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseProgress.put("startedAt", new TableInfo.Column("startedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourseProgress = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourseProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourseProgress = new TableInfo("course_progress", _columnsCourseProgress, _foreignKeysCourseProgress, _indicesCourseProgress);
        final TableInfo _existingCourseProgress = TableInfo.read(db, "course_progress");
        if (!_infoCourseProgress.equals(_existingCourseProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "course_progress(com.seecolab.istudy.data.model.CourseProgress).\n"
                  + " Expected:\n" + _infoCourseProgress + "\n"
                  + " Found:\n" + _existingCourseProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsQuestions = new HashMap<String, TableInfo.Column>(13);
        _columnsQuestions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("grade", new TableInfo.Column("grade", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("chapter", new TableInfo.Column("chapter", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("questionText", new TableInfo.Column("questionText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("questionType", new TableInfo.Column("questionType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("options", new TableInfo.Column("options", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("correctAnswer", new TableInfo.Column("correctAnswer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("explanation", new TableInfo.Column("explanation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("points", new TableInfo.Column("points", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestions.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuestions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuestions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuestions = new TableInfo("questions", _columnsQuestions, _foreignKeysQuestions, _indicesQuestions);
        final TableInfo _existingQuestions = TableInfo.read(db, "questions");
        if (!_infoQuestions.equals(_existingQuestions)) {
          return new RoomOpenHelper.ValidationResult(false, "questions(com.seecolab.istudy.data.model.Question).\n"
                  + " Expected:\n" + _infoQuestions + "\n"
                  + " Found:\n" + _existingQuestions);
        }
        final HashMap<String, TableInfo.Column> _columnsTestPapers = new HashMap<String, TableInfo.Column>(10);
        _columnsTestPapers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("grade", new TableInfo.Column("grade", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("examType", new TableInfo.Column("examType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("questionIds", new TableInfo.Column("questionIds", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("totalPoints", new TableInfo.Column("totalPoints", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("timeLimit", new TableInfo.Column("timeLimit", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestPapers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTestPapers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTestPapers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTestPapers = new TableInfo("test_papers", _columnsTestPapers, _foreignKeysTestPapers, _indicesTestPapers);
        final TableInfo _existingTestPapers = TableInfo.read(db, "test_papers");
        if (!_infoTestPapers.equals(_existingTestPapers)) {
          return new RoomOpenHelper.ValidationResult(false, "test_papers(com.seecolab.istudy.data.model.TestPaper).\n"
                  + " Expected:\n" + _infoTestPapers + "\n"
                  + " Found:\n" + _existingTestPapers);
        }
        final HashMap<String, TableInfo.Column> _columnsTestSubmissions = new HashMap<String, TableInfo.Column>(9);
        _columnsTestSubmissions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("testPaperId", new TableInfo.Column("testPaperId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("answers", new TableInfo.Column("answers", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("totalPoints", new TableInfo.Column("totalPoints", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("timeSpent", new TableInfo.Column("timeSpent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("submittedAt", new TableInfo.Column("submittedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTestSubmissions.put("startedAt", new TableInfo.Column("startedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTestSubmissions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTestSubmissions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTestSubmissions = new TableInfo("test_submissions", _columnsTestSubmissions, _foreignKeysTestSubmissions, _indicesTestSubmissions);
        final TableInfo _existingTestSubmissions = TableInfo.read(db, "test_submissions");
        if (!_infoTestSubmissions.equals(_existingTestSubmissions)) {
          return new RoomOpenHelper.ValidationResult(false, "test_submissions(com.seecolab.istudy.data.model.TestSubmission).\n"
                  + " Expected:\n" + _infoTestSubmissions + "\n"
                  + " Found:\n" + _existingTestSubmissions);
        }
        final HashMap<String, TableInfo.Column> _columnsHomeworkSubmissions = new HashMap<String, TableInfo.Column>(10);
        _columnsHomeworkSubmissions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("imagePath", new TableInfo.Column("imagePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("analysisResult", new TableInfo.Column("analysisResult", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("aiRecommendations", new TableInfo.Column("aiRecommendations", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("generatedTestPaperId", new TableInfo.Column("generatedTestPaperId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("submittedAt", new TableInfo.Column("submittedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeworkSubmissions.put("analyzedAt", new TableInfo.Column("analyzedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHomeworkSubmissions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHomeworkSubmissions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHomeworkSubmissions = new TableInfo("homework_submissions", _columnsHomeworkSubmissions, _foreignKeysHomeworkSubmissions, _indicesHomeworkSubmissions);
        final TableInfo _existingHomeworkSubmissions = TableInfo.read(db, "homework_submissions");
        if (!_infoHomeworkSubmissions.equals(_existingHomeworkSubmissions)) {
          return new RoomOpenHelper.ValidationResult(false, "homework_submissions(com.seecolab.istudy.data.model.HomeworkSubmission).\n"
                  + " Expected:\n" + _infoHomeworkSubmissions + "\n"
                  + " Found:\n" + _existingHomeworkSubmissions);
        }
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(5);
        _columnsUsers.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isLoggedIn", new TableInfo.Column("isLoggedIn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("lastLoginTime", new TableInfo.Column("lastLoginTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.seecolab.istudy.data.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsLearningReports = new HashMap<String, TableInfo.Column>(12);
        _columnsLearningReports.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("reportPeriodStart", new TableInfo.Column("reportPeriodStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("reportPeriodEnd", new TableInfo.Column("reportPeriodEnd", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("overallProgress", new TableInfo.Column("overallProgress", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("subjectScores", new TableInfo.Column("subjectScores", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("completedTests", new TableInfo.Column("completedTests", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("totalStudyTime", new TableInfo.Column("totalStudyTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("strengthAreas", new TableInfo.Column("strengthAreas", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("improvementAreas", new TableInfo.Column("improvementAreas", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("recommendations", new TableInfo.Column("recommendations", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningReports.put("generatedAt", new TableInfo.Column("generatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLearningReports = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLearningReports = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLearningReports = new TableInfo("learning_reports", _columnsLearningReports, _foreignKeysLearningReports, _indicesLearningReports);
        final TableInfo _existingLearningReports = TableInfo.read(db, "learning_reports");
        if (!_infoLearningReports.equals(_existingLearningReports)) {
          return new RoomOpenHelper.ValidationResult(false, "learning_reports(com.seecolab.istudy.data.model.LearningReport).\n"
                  + " Expected:\n" + _infoLearningReports + "\n"
                  + " Found:\n" + _existingLearningReports);
        }
        final HashMap<String, TableInfo.Column> _columnsStudySessions = new HashMap<String, TableInfo.Column>(9);
        _columnsStudySessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("activityType", new TableInfo.Column("activityType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("score", new TableInfo.Column("score", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("completionRate", new TableInfo.Column("completionRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("startedAt", new TableInfo.Column("startedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudySessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudySessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudySessions = new TableInfo("study_sessions", _columnsStudySessions, _foreignKeysStudySessions, _indicesStudySessions);
        final TableInfo _existingStudySessions = TableInfo.read(db, "study_sessions");
        if (!_infoStudySessions.equals(_existingStudySessions)) {
          return new RoomOpenHelper.ValidationResult(false, "study_sessions(com.seecolab.istudy.data.model.StudySession).\n"
                  + " Expected:\n" + _infoStudySessions + "\n"
                  + " Found:\n" + _existingStudySessions);
        }
        final HashMap<String, TableInfo.Column> _columnsTeachers = new HashMap<String, TableInfo.Column>(14);
        _columnsTeachers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("subjects", new TableInfo.Column("subjects", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("experience", new TableInfo.Column("experience", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("rating", new TableInfo.Column("rating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("hourlyRate", new TableInfo.Column("hourlyRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("avatar", new TableInfo.Column("avatar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("isAvailable", new TableInfo.Column("isAvailable", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("qualifications", new TableInfo.Column("qualifications", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeachers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTeachers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTeachers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTeachers = new TableInfo("teachers", _columnsTeachers, _foreignKeysTeachers, _indicesTeachers);
        final TableInfo _existingTeachers = TableInfo.read(db, "teachers");
        if (!_infoTeachers.equals(_existingTeachers)) {
          return new RoomOpenHelper.ValidationResult(false, "teachers(com.seecolab.istudy.data.model.Teacher).\n"
                  + " Expected:\n" + _infoTeachers + "\n"
                  + " Found:\n" + _existingTeachers);
        }
        final HashMap<String, TableInfo.Column> _columnsTeacherBookings = new HashMap<String, TableInfo.Column>(9);
        _columnsTeacherBookings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("teacherId", new TableInfo.Column("teacherId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("scheduledTime", new TableInfo.Column("scheduledTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherBookings.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTeacherBookings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTeacherBookings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTeacherBookings = new TableInfo("teacher_bookings", _columnsTeacherBookings, _foreignKeysTeacherBookings, _indicesTeacherBookings);
        final TableInfo _existingTeacherBookings = TableInfo.read(db, "teacher_bookings");
        if (!_infoTeacherBookings.equals(_existingTeacherBookings)) {
          return new RoomOpenHelper.ValidationResult(false, "teacher_bookings(com.seecolab.istudy.data.model.TeacherBooking).\n"
                  + " Expected:\n" + _infoTeacherBookings + "\n"
                  + " Found:\n" + _existingTeacherBookings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "aebdbd921ae060faea2e9ff59063efbc", "afe4546c1427840ec3eb241dd61caf9f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "students","courses","course_progress","questions","test_papers","test_submissions","homework_submissions","users","learning_reports","study_sessions","teachers","teacher_bookings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `students`");
      _db.execSQL("DELETE FROM `courses`");
      _db.execSQL("DELETE FROM `course_progress`");
      _db.execSQL("DELETE FROM `questions`");
      _db.execSQL("DELETE FROM `test_papers`");
      _db.execSQL("DELETE FROM `test_submissions`");
      _db.execSQL("DELETE FROM `homework_submissions`");
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `learning_reports`");
      _db.execSQL("DELETE FROM `study_sessions`");
      _db.execSQL("DELETE FROM `teachers`");
      _db.execSQL("DELETE FROM `teacher_bookings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(StudentDao.class, StudentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CourseDao.class, CourseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CourseProgressDao.class, CourseProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuestionDao.class, QuestionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TestPaperDao.class, TestPaperDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TestSubmissionDao.class, TestSubmissionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HomeworkDao.class, HomeworkDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LearningReportDao.class, LearningReportDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StudySessionDao.class, StudySessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TeacherDao.class, TeacherDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TeacherBookingDao.class, TeacherBookingDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public StudentDao studentDao() {
    if (_studentDao != null) {
      return _studentDao;
    } else {
      synchronized(this) {
        if(_studentDao == null) {
          _studentDao = new StudentDao_Impl(this);
        }
        return _studentDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public CourseDao courseDao() {
    if (_courseDao != null) {
      return _courseDao;
    } else {
      synchronized(this) {
        if(_courseDao == null) {
          _courseDao = new CourseDao_Impl(this);
        }
        return _courseDao;
      }
    }
  }

  @Override
  public CourseProgressDao courseProgressDao() {
    if (_courseProgressDao != null) {
      return _courseProgressDao;
    } else {
      synchronized(this) {
        if(_courseProgressDao == null) {
          _courseProgressDao = new CourseProgressDao_Impl(this);
        }
        return _courseProgressDao;
      }
    }
  }

  @Override
  public QuestionDao questionDao() {
    if (_questionDao != null) {
      return _questionDao;
    } else {
      synchronized(this) {
        if(_questionDao == null) {
          _questionDao = new QuestionDao_Impl(this);
        }
        return _questionDao;
      }
    }
  }

  @Override
  public TestPaperDao testPaperDao() {
    if (_testPaperDao != null) {
      return _testPaperDao;
    } else {
      synchronized(this) {
        if(_testPaperDao == null) {
          _testPaperDao = new TestPaperDao_Impl(this);
        }
        return _testPaperDao;
      }
    }
  }

  @Override
  public TestSubmissionDao testSubmissionDao() {
    if (_testSubmissionDao != null) {
      return _testSubmissionDao;
    } else {
      synchronized(this) {
        if(_testSubmissionDao == null) {
          _testSubmissionDao = new TestSubmissionDao_Impl(this);
        }
        return _testSubmissionDao;
      }
    }
  }

  @Override
  public HomeworkDao homeworkDao() {
    if (_homeworkDao != null) {
      return _homeworkDao;
    } else {
      synchronized(this) {
        if(_homeworkDao == null) {
          _homeworkDao = new HomeworkDao_Impl(this);
        }
        return _homeworkDao;
      }
    }
  }

  @Override
  public LearningReportDao learningReportDao() {
    if (_learningReportDao != null) {
      return _learningReportDao;
    } else {
      synchronized(this) {
        if(_learningReportDao == null) {
          _learningReportDao = new LearningReportDao_Impl(this);
        }
        return _learningReportDao;
      }
    }
  }

  @Override
  public StudySessionDao studySessionDao() {
    if (_studySessionDao != null) {
      return _studySessionDao;
    } else {
      synchronized(this) {
        if(_studySessionDao == null) {
          _studySessionDao = new StudySessionDao_Impl(this);
        }
        return _studySessionDao;
      }
    }
  }

  @Override
  public TeacherDao teacherDao() {
    if (_teacherDao != null) {
      return _teacherDao;
    } else {
      synchronized(this) {
        if(_teacherDao == null) {
          _teacherDao = new TeacherDao_Impl(this);
        }
        return _teacherDao;
      }
    }
  }

  @Override
  public TeacherBookingDao teacherBookingDao() {
    if (_teacherBookingDao != null) {
      return _teacherBookingDao;
    } else {
      synchronized(this) {
        if(_teacherBookingDao == null) {
          _teacherBookingDao = new TeacherBookingDao_Impl(this);
        }
        return _teacherBookingDao;
      }
    }
  }
}

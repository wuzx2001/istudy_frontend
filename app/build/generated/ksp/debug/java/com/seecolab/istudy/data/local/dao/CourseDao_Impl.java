package com.seecolab.istudy.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.seecolab.istudy.data.local.converter.TypeConverters;
import com.seecolab.istudy.data.model.Course;
import com.seecolab.istudy.data.model.Difficulty;
import com.seecolab.istudy.data.model.Grade;
import com.seecolab.istudy.data.model.Subject;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CourseDao_Impl implements CourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Course> __insertionAdapterOfCourse;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<Course> __deletionAdapterOfCourse;

  private final EntityDeletionOrUpdateAdapter<Course> __updateAdapterOfCourse;

  public CourseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourse = new EntityInsertionAdapter<Course>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `courses` (`id`,`subject`,`grade`,`chapterName`,`description`,`keyPoints`,`difficulty`,`estimatedStudyTime`,`prerequisites`,`learningObjectives`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Course entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __Subject_enumToString(entity.getSubject()));
        statement.bindString(3, __Grade_enumToString(entity.getGrade()));
        statement.bindString(4, entity.getChapterName());
        statement.bindString(5, entity.getDescription());
        final String _tmp = __typeConverters.fromStringList(entity.getKeyPoints());
        statement.bindString(6, _tmp);
        statement.bindString(7, __Difficulty_enumToString(entity.getDifficulty()));
        statement.bindLong(8, entity.getEstimatedStudyTime());
        final String _tmp_1 = __typeConverters.fromStringList(entity.getPrerequisites());
        statement.bindString(9, _tmp_1);
        final String _tmp_2 = __typeConverters.fromStringList(entity.getLearningObjectives());
        statement.bindString(10, _tmp_2);
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfCourse = new EntityDeletionOrUpdateAdapter<Course>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `courses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Course entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCourse = new EntityDeletionOrUpdateAdapter<Course>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `courses` SET `id` = ?,`subject` = ?,`grade` = ?,`chapterName` = ?,`description` = ?,`keyPoints` = ?,`difficulty` = ?,`estimatedStudyTime` = ?,`prerequisites` = ?,`learningObjectives` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Course entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __Subject_enumToString(entity.getSubject()));
        statement.bindString(3, __Grade_enumToString(entity.getGrade()));
        statement.bindString(4, entity.getChapterName());
        statement.bindString(5, entity.getDescription());
        final String _tmp = __typeConverters.fromStringList(entity.getKeyPoints());
        statement.bindString(6, _tmp);
        statement.bindString(7, __Difficulty_enumToString(entity.getDifficulty()));
        statement.bindLong(8, entity.getEstimatedStudyTime());
        final String _tmp_1 = __typeConverters.fromStringList(entity.getPrerequisites());
        statement.bindString(9, _tmp_1);
        final String _tmp_2 = __typeConverters.fromStringList(entity.getLearningObjectives());
        statement.bindString(10, _tmp_2);
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public Object insertCourse(final Course course, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCourse.insertAndReturnId(course);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCourses(final List<Course> courses,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCourse.insert(courses);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCourse(final Course course, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCourse.handle(course);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCourse(final Course course, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCourse.handle(course);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCourseById(final long id, final Continuation<? super Course> $completion) {
    final String _sql = "SELECT * FROM courses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Course>() {
      @Override
      @Nullable
      public Course call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfChapterName = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfKeyPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "keyPoints");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfEstimatedStudyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedStudyTime");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfLearningObjectives = CursorUtil.getColumnIndexOrThrow(_cursor, "learningObjectives");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Course _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final String _tmpChapterName;
            _tmpChapterName = _cursor.getString(_cursorIndexOfChapterName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpKeyPoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfKeyPoints);
            _tmpKeyPoints = __typeConverters.toStringList(_tmp);
            final Difficulty _tmpDifficulty;
            _tmpDifficulty = __Difficulty_stringToEnum(_cursor.getString(_cursorIndexOfDifficulty));
            final int _tmpEstimatedStudyTime;
            _tmpEstimatedStudyTime = _cursor.getInt(_cursorIndexOfEstimatedStudyTime);
            final List<String> _tmpPrerequisites;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfPrerequisites);
            _tmpPrerequisites = __typeConverters.toStringList(_tmp_1);
            final List<String> _tmpLearningObjectives;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLearningObjectives);
            _tmpLearningObjectives = __typeConverters.toStringList(_tmp_2);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Course(_tmpId,_tmpSubject,_tmpGrade,_tmpChapterName,_tmpDescription,_tmpKeyPoints,_tmpDifficulty,_tmpEstimatedStudyTime,_tmpPrerequisites,_tmpLearningObjectives,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Course>> getCoursesBySubjectAndGrade(final Subject subject, final Grade grade) {
    final String _sql = "SELECT * FROM courses WHERE subject = ? AND grade = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __Subject_enumToString(subject));
    _argIndex = 2;
    _statement.bindString(_argIndex, __Grade_enumToString(grade));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"courses"}, new Callable<List<Course>>() {
      @Override
      @NonNull
      public List<Course> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfChapterName = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfKeyPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "keyPoints");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfEstimatedStudyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedStudyTime");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfLearningObjectives = CursorUtil.getColumnIndexOrThrow(_cursor, "learningObjectives");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Course> _result = new ArrayList<Course>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Course _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final String _tmpChapterName;
            _tmpChapterName = _cursor.getString(_cursorIndexOfChapterName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpKeyPoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfKeyPoints);
            _tmpKeyPoints = __typeConverters.toStringList(_tmp);
            final Difficulty _tmpDifficulty;
            _tmpDifficulty = __Difficulty_stringToEnum(_cursor.getString(_cursorIndexOfDifficulty));
            final int _tmpEstimatedStudyTime;
            _tmpEstimatedStudyTime = _cursor.getInt(_cursorIndexOfEstimatedStudyTime);
            final List<String> _tmpPrerequisites;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfPrerequisites);
            _tmpPrerequisites = __typeConverters.toStringList(_tmp_1);
            final List<String> _tmpLearningObjectives;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLearningObjectives);
            _tmpLearningObjectives = __typeConverters.toStringList(_tmp_2);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Course(_tmpId,_tmpSubject,_tmpGrade,_tmpChapterName,_tmpDescription,_tmpKeyPoints,_tmpDifficulty,_tmpEstimatedStudyTime,_tmpPrerequisites,_tmpLearningObjectives,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Course>> getCoursesByGrade(final Grade grade) {
    final String _sql = "SELECT * FROM courses WHERE grade = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __Grade_enumToString(grade));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"courses"}, new Callable<List<Course>>() {
      @Override
      @NonNull
      public List<Course> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfChapterName = CursorUtil.getColumnIndexOrThrow(_cursor, "chapterName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfKeyPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "keyPoints");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfEstimatedStudyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedStudyTime");
          final int _cursorIndexOfPrerequisites = CursorUtil.getColumnIndexOrThrow(_cursor, "prerequisites");
          final int _cursorIndexOfLearningObjectives = CursorUtil.getColumnIndexOrThrow(_cursor, "learningObjectives");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Course> _result = new ArrayList<Course>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Course _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final String _tmpChapterName;
            _tmpChapterName = _cursor.getString(_cursorIndexOfChapterName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final List<String> _tmpKeyPoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfKeyPoints);
            _tmpKeyPoints = __typeConverters.toStringList(_tmp);
            final Difficulty _tmpDifficulty;
            _tmpDifficulty = __Difficulty_stringToEnum(_cursor.getString(_cursorIndexOfDifficulty));
            final int _tmpEstimatedStudyTime;
            _tmpEstimatedStudyTime = _cursor.getInt(_cursorIndexOfEstimatedStudyTime);
            final List<String> _tmpPrerequisites;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfPrerequisites);
            _tmpPrerequisites = __typeConverters.toStringList(_tmp_1);
            final List<String> _tmpLearningObjectives;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLearningObjectives);
            _tmpLearningObjectives = __typeConverters.toStringList(_tmp_2);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Course(_tmpId,_tmpSubject,_tmpGrade,_tmpChapterName,_tmpDescription,_tmpKeyPoints,_tmpDifficulty,_tmpEstimatedStudyTime,_tmpPrerequisites,_tmpLearningObjectives,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __Subject_enumToString(@NonNull final Subject _value) {
    switch (_value) {
      case CHINESE: return "CHINESE";
      case MATH: return "MATH";
      case ENGLISH: return "ENGLISH";
      case PHYSICS: return "PHYSICS";
      case CHEMISTRY: return "CHEMISTRY";
      case BIOLOGY: return "BIOLOGY";
      case HISTORY: return "HISTORY";
      case GEOGRAPHY: return "GEOGRAPHY";
      case POLITICS: return "POLITICS";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __Grade_enumToString(@NonNull final Grade _value) {
    switch (_value) {
      case GRADE_1: return "GRADE_1";
      case GRADE_2: return "GRADE_2";
      case GRADE_3: return "GRADE_3";
      case GRADE_4: return "GRADE_4";
      case GRADE_5: return "GRADE_5";
      case GRADE_6: return "GRADE_6";
      case GRADE_7: return "GRADE_7";
      case GRADE_8: return "GRADE_8";
      case GRADE_9: return "GRADE_9";
      case GRADE_10: return "GRADE_10";
      case GRADE_11: return "GRADE_11";
      case GRADE_12: return "GRADE_12";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __Difficulty_enumToString(@NonNull final Difficulty _value) {
    switch (_value) {
      case BEGINNER: return "BEGINNER";
      case ELEMENTARY: return "ELEMENTARY";
      case INTERMEDIATE: return "INTERMEDIATE";
      case ADVANCED: return "ADVANCED";
      case EXPERT: return "EXPERT";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private Subject __Subject_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "CHINESE": return Subject.CHINESE;
      case "MATH": return Subject.MATH;
      case "ENGLISH": return Subject.ENGLISH;
      case "PHYSICS": return Subject.PHYSICS;
      case "CHEMISTRY": return Subject.CHEMISTRY;
      case "BIOLOGY": return Subject.BIOLOGY;
      case "HISTORY": return Subject.HISTORY;
      case "GEOGRAPHY": return Subject.GEOGRAPHY;
      case "POLITICS": return Subject.POLITICS;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private Grade __Grade_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "GRADE_1": return Grade.GRADE_1;
      case "GRADE_2": return Grade.GRADE_2;
      case "GRADE_3": return Grade.GRADE_3;
      case "GRADE_4": return Grade.GRADE_4;
      case "GRADE_5": return Grade.GRADE_5;
      case "GRADE_6": return Grade.GRADE_6;
      case "GRADE_7": return Grade.GRADE_7;
      case "GRADE_8": return Grade.GRADE_8;
      case "GRADE_9": return Grade.GRADE_9;
      case "GRADE_10": return Grade.GRADE_10;
      case "GRADE_11": return Grade.GRADE_11;
      case "GRADE_12": return Grade.GRADE_12;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private Difficulty __Difficulty_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "BEGINNER": return Difficulty.BEGINNER;
      case "ELEMENTARY": return Difficulty.ELEMENTARY;
      case "INTERMEDIATE": return Difficulty.INTERMEDIATE;
      case "ADVANCED": return Difficulty.ADVANCED;
      case "EXPERT": return Difficulty.EXPERT;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

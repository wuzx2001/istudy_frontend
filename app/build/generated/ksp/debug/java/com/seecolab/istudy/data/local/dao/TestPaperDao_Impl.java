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
import com.seecolab.istudy.data.model.ExamType;
import com.seecolab.istudy.data.model.Grade;
import com.seecolab.istudy.data.model.Subject;
import com.seecolab.istudy.data.model.TestPaper;
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
public final class TestPaperDao_Impl implements TestPaperDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TestPaper> __insertionAdapterOfTestPaper;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<TestPaper> __deletionAdapterOfTestPaper;

  private final EntityDeletionOrUpdateAdapter<TestPaper> __updateAdapterOfTestPaper;

  public TestPaperDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTestPaper = new EntityInsertionAdapter<TestPaper>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `test_papers` (`id`,`title`,`subject`,`grade`,`examType`,`questionIds`,`totalPoints`,`timeLimit`,`description`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TestPaper entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, __Subject_enumToString(entity.getSubject()));
        statement.bindString(4, __Grade_enumToString(entity.getGrade()));
        statement.bindString(5, __ExamType_enumToString(entity.getExamType()));
        final String _tmp = __typeConverters.fromLongList(entity.getQuestionIds());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getTotalPoints());
        statement.bindLong(8, entity.getTimeLimit());
        if (entity.getDescription() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDescription());
        }
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfTestPaper = new EntityDeletionOrUpdateAdapter<TestPaper>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `test_papers` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TestPaper entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTestPaper = new EntityDeletionOrUpdateAdapter<TestPaper>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `test_papers` SET `id` = ?,`title` = ?,`subject` = ?,`grade` = ?,`examType` = ?,`questionIds` = ?,`totalPoints` = ?,`timeLimit` = ?,`description` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TestPaper entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, __Subject_enumToString(entity.getSubject()));
        statement.bindString(4, __Grade_enumToString(entity.getGrade()));
        statement.bindString(5, __ExamType_enumToString(entity.getExamType()));
        final String _tmp = __typeConverters.fromLongList(entity.getQuestionIds());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getTotalPoints());
        statement.bindLong(8, entity.getTimeLimit());
        if (entity.getDescription() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDescription());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insertTestPaper(final TestPaper testPaper,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTestPaper.insertAndReturnId(testPaper);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTestPaper(final TestPaper testPaper,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTestPaper.handle(testPaper);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTestPaper(final TestPaper testPaper,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTestPaper.handle(testPaper);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTestPaperById(final long id, final Continuation<? super TestPaper> $completion) {
    final String _sql = "SELECT * FROM test_papers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TestPaper>() {
      @Override
      @Nullable
      public TestPaper call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfExamType = CursorUtil.getColumnIndexOrThrow(_cursor, "examType");
          final int _cursorIndexOfQuestionIds = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIds");
          final int _cursorIndexOfTotalPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPoints");
          final int _cursorIndexOfTimeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLimit");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final TestPaper _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final ExamType _tmpExamType;
            _tmpExamType = __ExamType_stringToEnum(_cursor.getString(_cursorIndexOfExamType));
            final List<Long> _tmpQuestionIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfQuestionIds);
            _tmpQuestionIds = __typeConverters.toLongList(_tmp);
            final int _tmpTotalPoints;
            _tmpTotalPoints = _cursor.getInt(_cursorIndexOfTotalPoints);
            final int _tmpTimeLimit;
            _tmpTimeLimit = _cursor.getInt(_cursorIndexOfTimeLimit);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new TestPaper(_tmpId,_tmpTitle,_tmpSubject,_tmpGrade,_tmpExamType,_tmpQuestionIds,_tmpTotalPoints,_tmpTimeLimit,_tmpDescription,_tmpCreatedAt);
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
  public Flow<List<TestPaper>> getTestPapersBySubjectAndGrade(final Subject subject,
      final Grade grade) {
    final String _sql = "SELECT * FROM test_papers WHERE subject = ? AND grade = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __Subject_enumToString(subject));
    _argIndex = 2;
    _statement.bindString(_argIndex, __Grade_enumToString(grade));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"test_papers"}, new Callable<List<TestPaper>>() {
      @Override
      @NonNull
      public List<TestPaper> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfExamType = CursorUtil.getColumnIndexOrThrow(_cursor, "examType");
          final int _cursorIndexOfQuestionIds = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIds");
          final int _cursorIndexOfTotalPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPoints");
          final int _cursorIndexOfTimeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLimit");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<TestPaper> _result = new ArrayList<TestPaper>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TestPaper _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final ExamType _tmpExamType;
            _tmpExamType = __ExamType_stringToEnum(_cursor.getString(_cursorIndexOfExamType));
            final List<Long> _tmpQuestionIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfQuestionIds);
            _tmpQuestionIds = __typeConverters.toLongList(_tmp);
            final int _tmpTotalPoints;
            _tmpTotalPoints = _cursor.getInt(_cursorIndexOfTotalPoints);
            final int _tmpTimeLimit;
            _tmpTimeLimit = _cursor.getInt(_cursorIndexOfTimeLimit);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new TestPaper(_tmpId,_tmpTitle,_tmpSubject,_tmpGrade,_tmpExamType,_tmpQuestionIds,_tmpTotalPoints,_tmpTimeLimit,_tmpDescription,_tmpCreatedAt);
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
  public Flow<List<TestPaper>> getTestPapersByExamType(final ExamType examType) {
    final String _sql = "SELECT * FROM test_papers WHERE examType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __ExamType_enumToString(examType));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"test_papers"}, new Callable<List<TestPaper>>() {
      @Override
      @NonNull
      public List<TestPaper> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfExamType = CursorUtil.getColumnIndexOrThrow(_cursor, "examType");
          final int _cursorIndexOfQuestionIds = CursorUtil.getColumnIndexOrThrow(_cursor, "questionIds");
          final int _cursorIndexOfTotalPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPoints");
          final int _cursorIndexOfTimeLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLimit");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<TestPaper> _result = new ArrayList<TestPaper>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TestPaper _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final ExamType _tmpExamType;
            _tmpExamType = __ExamType_stringToEnum(_cursor.getString(_cursorIndexOfExamType));
            final List<Long> _tmpQuestionIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfQuestionIds);
            _tmpQuestionIds = __typeConverters.toLongList(_tmp);
            final int _tmpTotalPoints;
            _tmpTotalPoints = _cursor.getInt(_cursorIndexOfTotalPoints);
            final int _tmpTimeLimit;
            _tmpTimeLimit = _cursor.getInt(_cursorIndexOfTimeLimit);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new TestPaper(_tmpId,_tmpTitle,_tmpSubject,_tmpGrade,_tmpExamType,_tmpQuestionIds,_tmpTotalPoints,_tmpTimeLimit,_tmpDescription,_tmpCreatedAt);
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

  private String __ExamType_enumToString(@NonNull final ExamType _value) {
    switch (_value) {
      case UNIT: return "UNIT";
      case MIDTERM: return "MIDTERM";
      case FINAL: return "FINAL";
      case PRACTICE: return "PRACTICE";
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

  private ExamType __ExamType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "UNIT": return ExamType.UNIT;
      case "MIDTERM": return ExamType.MIDTERM;
      case "FINAL": return ExamType.FINAL;
      case "PRACTICE": return ExamType.PRACTICE;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

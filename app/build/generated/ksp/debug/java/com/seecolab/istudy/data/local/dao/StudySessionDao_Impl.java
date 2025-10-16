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
import com.seecolab.istudy.data.model.ActivityType;
import com.seecolab.istudy.data.model.StudySession;
import com.seecolab.istudy.data.model.Subject;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
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
public final class StudySessionDao_Impl implements StudySessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StudySession> __insertionAdapterOfStudySession;

  private final EntityDeletionOrUpdateAdapter<StudySession> __deletionAdapterOfStudySession;

  private final EntityDeletionOrUpdateAdapter<StudySession> __updateAdapterOfStudySession;

  public StudySessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudySession = new EntityInsertionAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `study_sessions` (`id`,`studentId`,`subject`,`activityType`,`duration`,`score`,`completionRate`,`startedAt`,`completedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindString(3, __Subject_enumToString(entity.getSubject()));
        statement.bindString(4, __ActivityType_enumToString(entity.getActivityType()));
        statement.bindLong(5, entity.getDuration());
        if (entity.getScore() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getScore());
        }
        statement.bindDouble(7, entity.getCompletionRate());
        statement.bindLong(8, entity.getStartedAt());
        statement.bindLong(9, entity.getCompletedAt());
      }
    };
    this.__deletionAdapterOfStudySession = new EntityDeletionOrUpdateAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `study_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudySession = new EntityDeletionOrUpdateAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `study_sessions` SET `id` = ?,`studentId` = ?,`subject` = ?,`activityType` = ?,`duration` = ?,`score` = ?,`completionRate` = ?,`startedAt` = ?,`completedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindString(3, __Subject_enumToString(entity.getSubject()));
        statement.bindString(4, __ActivityType_enumToString(entity.getActivityType()));
        statement.bindLong(5, entity.getDuration());
        if (entity.getScore() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getScore());
        }
        statement.bindDouble(7, entity.getCompletionRate());
        statement.bindLong(8, entity.getStartedAt());
        statement.bindLong(9, entity.getCompletedAt());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertSession(final StudySession session,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStudySession.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSession(final StudySession session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStudySession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSession(final StudySession session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStudySession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSessionById(final long id,
      final Continuation<? super StudySession> $completion) {
    final String _sql = "SELECT * FROM study_sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StudySession>() {
      @Override
      @Nullable
      public StudySession call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfActivityType = CursorUtil.getColumnIndexOrThrow(_cursor, "activityType");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfCompletionRate = CursorUtil.getColumnIndexOrThrow(_cursor, "completionRate");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final StudySession _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final ActivityType _tmpActivityType;
            _tmpActivityType = __ActivityType_stringToEnum(_cursor.getString(_cursorIndexOfActivityType));
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final Float _tmpScore;
            if (_cursor.isNull(_cursorIndexOfScore)) {
              _tmpScore = null;
            } else {
              _tmpScore = _cursor.getFloat(_cursorIndexOfScore);
            }
            final float _tmpCompletionRate;
            _tmpCompletionRate = _cursor.getFloat(_cursorIndexOfCompletionRate);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _result = new StudySession(_tmpId,_tmpStudentId,_tmpSubject,_tmpActivityType,_tmpDuration,_tmpScore,_tmpCompletionRate,_tmpStartedAt,_tmpCompletedAt);
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
  public Flow<List<StudySession>> getSessionsByStudent(final long studentId) {
    final String _sql = "SELECT * FROM study_sessions WHERE studentId = ? ORDER BY startedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySession>>() {
      @Override
      @NonNull
      public List<StudySession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfActivityType = CursorUtil.getColumnIndexOrThrow(_cursor, "activityType");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfCompletionRate = CursorUtil.getColumnIndexOrThrow(_cursor, "completionRate");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final ActivityType _tmpActivityType;
            _tmpActivityType = __ActivityType_stringToEnum(_cursor.getString(_cursorIndexOfActivityType));
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final Float _tmpScore;
            if (_cursor.isNull(_cursorIndexOfScore)) {
              _tmpScore = null;
            } else {
              _tmpScore = _cursor.getFloat(_cursorIndexOfScore);
            }
            final float _tmpCompletionRate;
            _tmpCompletionRate = _cursor.getFloat(_cursorIndexOfCompletionRate);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _item = new StudySession(_tmpId,_tmpStudentId,_tmpSubject,_tmpActivityType,_tmpDuration,_tmpScore,_tmpCompletionRate,_tmpStartedAt,_tmpCompletedAt);
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
  public Flow<List<StudySession>> getSessionsByStudentAndSubject(final long studentId,
      final Subject subject) {
    final String _sql = "SELECT * FROM study_sessions WHERE studentId = ? AND subject = ? ORDER BY startedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    _argIndex = 2;
    _statement.bindString(_argIndex, __Subject_enumToString(subject));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySession>>() {
      @Override
      @NonNull
      public List<StudySession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfActivityType = CursorUtil.getColumnIndexOrThrow(_cursor, "activityType");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfCompletionRate = CursorUtil.getColumnIndexOrThrow(_cursor, "completionRate");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final ActivityType _tmpActivityType;
            _tmpActivityType = __ActivityType_stringToEnum(_cursor.getString(_cursorIndexOfActivityType));
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final Float _tmpScore;
            if (_cursor.isNull(_cursorIndexOfScore)) {
              _tmpScore = null;
            } else {
              _tmpScore = _cursor.getFloat(_cursorIndexOfScore);
            }
            final float _tmpCompletionRate;
            _tmpCompletionRate = _cursor.getFloat(_cursorIndexOfCompletionRate);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _item = new StudySession(_tmpId,_tmpStudentId,_tmpSubject,_tmpActivityType,_tmpDuration,_tmpScore,_tmpCompletionRate,_tmpStartedAt,_tmpCompletedAt);
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

  private String __ActivityType_enumToString(@NonNull final ActivityType _value) {
    switch (_value) {
      case HOMEWORK_CORRECTION: return "HOMEWORK_CORRECTION";
      case COURSE_STUDY: return "COURSE_STUDY";
      case PRACTICE_TEST: return "PRACTICE_TEST";
      case EXAM_PREPARATION: return "EXAM_PREPARATION";
      case TEACHER_SESSION: return "TEACHER_SESSION";
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

  private ActivityType __ActivityType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "HOMEWORK_CORRECTION": return ActivityType.HOMEWORK_CORRECTION;
      case "COURSE_STUDY": return ActivityType.COURSE_STUDY;
      case "PRACTICE_TEST": return ActivityType.PRACTICE_TEST;
      case "EXAM_PREPARATION": return ActivityType.EXAM_PREPARATION;
      case "TEACHER_SESSION": return ActivityType.TEACHER_SESSION;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

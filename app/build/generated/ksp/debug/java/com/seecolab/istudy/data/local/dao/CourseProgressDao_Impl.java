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
import com.seecolab.istudy.data.model.CourseProgress;
import java.lang.Class;
import java.lang.Exception;
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
public final class CourseProgressDao_Impl implements CourseProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CourseProgress> __insertionAdapterOfCourseProgress;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<CourseProgress> __deletionAdapterOfCourseProgress;

  private final EntityDeletionOrUpdateAdapter<CourseProgress> __updateAdapterOfCourseProgress;

  public CourseProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseProgress = new EntityInsertionAdapter<CourseProgress>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `course_progress` (`id`,`studentId`,`courseId`,`progress`,`completedKeyPoints`,`timeSpent`,`lastAccessedAt`,`startedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseProgress entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getCourseId());
        statement.bindDouble(4, entity.getProgress());
        final String _tmp = __typeConverters.fromStringList(entity.getCompletedKeyPoints());
        statement.bindString(5, _tmp);
        statement.bindLong(6, entity.getTimeSpent());
        statement.bindLong(7, entity.getLastAccessedAt());
        statement.bindLong(8, entity.getStartedAt());
      }
    };
    this.__deletionAdapterOfCourseProgress = new EntityDeletionOrUpdateAdapter<CourseProgress>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `course_progress` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseProgress entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCourseProgress = new EntityDeletionOrUpdateAdapter<CourseProgress>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `course_progress` SET `id` = ?,`studentId` = ?,`courseId` = ?,`progress` = ?,`completedKeyPoints` = ?,`timeSpent` = ?,`lastAccessedAt` = ?,`startedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseProgress entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getCourseId());
        statement.bindDouble(4, entity.getProgress());
        final String _tmp = __typeConverters.fromStringList(entity.getCompletedKeyPoints());
        statement.bindString(5, _tmp);
        statement.bindLong(6, entity.getTimeSpent());
        statement.bindLong(7, entity.getLastAccessedAt());
        statement.bindLong(8, entity.getStartedAt());
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insertProgress(final CourseProgress progress,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCourseProgress.insertAndReturnId(progress);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProgress(final CourseProgress progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCourseProgress.handle(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProgress(final CourseProgress progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCourseProgress.handle(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getProgress(final long studentId, final long courseId,
      final Continuation<? super CourseProgress> $completion) {
    final String _sql = "SELECT * FROM course_progress WHERE studentId = ? AND courseId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, courseId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CourseProgress>() {
      @Override
      @Nullable
      public CourseProgress call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfCompletedKeyPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "completedKeyPoints");
          final int _cursorIndexOfTimeSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSpent");
          final int _cursorIndexOfLastAccessedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessedAt");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final CourseProgress _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpCourseId;
            _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final List<String> _tmpCompletedKeyPoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCompletedKeyPoints);
            _tmpCompletedKeyPoints = __typeConverters.toStringList(_tmp);
            final int _tmpTimeSpent;
            _tmpTimeSpent = _cursor.getInt(_cursorIndexOfTimeSpent);
            final long _tmpLastAccessedAt;
            _tmpLastAccessedAt = _cursor.getLong(_cursorIndexOfLastAccessedAt);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _result = new CourseProgress(_tmpId,_tmpStudentId,_tmpCourseId,_tmpProgress,_tmpCompletedKeyPoints,_tmpTimeSpent,_tmpLastAccessedAt,_tmpStartedAt);
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
  public Flow<List<CourseProgress>> getStudentProgress(final long studentId) {
    final String _sql = "SELECT * FROM course_progress WHERE studentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"course_progress"}, new Callable<List<CourseProgress>>() {
      @Override
      @NonNull
      public List<CourseProgress> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
          final int _cursorIndexOfProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "progress");
          final int _cursorIndexOfCompletedKeyPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "completedKeyPoints");
          final int _cursorIndexOfTimeSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSpent");
          final int _cursorIndexOfLastAccessedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAccessedAt");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<CourseProgress> _result = new ArrayList<CourseProgress>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseProgress _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpCourseId;
            _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
            final float _tmpProgress;
            _tmpProgress = _cursor.getFloat(_cursorIndexOfProgress);
            final List<String> _tmpCompletedKeyPoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCompletedKeyPoints);
            _tmpCompletedKeyPoints = __typeConverters.toStringList(_tmp);
            final int _tmpTimeSpent;
            _tmpTimeSpent = _cursor.getInt(_cursorIndexOfTimeSpent);
            final long _tmpLastAccessedAt;
            _tmpLastAccessedAt = _cursor.getLong(_cursorIndexOfLastAccessedAt);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new CourseProgress(_tmpId,_tmpStudentId,_tmpCourseId,_tmpProgress,_tmpCompletedKeyPoints,_tmpTimeSpent,_tmpLastAccessedAt,_tmpStartedAt);
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
}

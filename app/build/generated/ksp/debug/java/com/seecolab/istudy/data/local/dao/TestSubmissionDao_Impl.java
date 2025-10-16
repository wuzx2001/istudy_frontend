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
import com.seecolab.istudy.data.model.TestSubmission;
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
import java.util.Map;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TestSubmissionDao_Impl implements TestSubmissionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TestSubmission> __insertionAdapterOfTestSubmission;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<TestSubmission> __deletionAdapterOfTestSubmission;

  private final EntityDeletionOrUpdateAdapter<TestSubmission> __updateAdapterOfTestSubmission;

  public TestSubmissionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTestSubmission = new EntityInsertionAdapter<TestSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `test_submissions` (`id`,`studentId`,`testPaperId`,`answers`,`score`,`totalPoints`,`timeSpent`,`submittedAt`,`startedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TestSubmission entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getTestPaperId());
        final String _tmp = __typeConverters.fromLongStringMap(entity.getAnswers());
        statement.bindString(4, _tmp);
        statement.bindLong(5, entity.getScore());
        statement.bindLong(6, entity.getTotalPoints());
        statement.bindLong(7, entity.getTimeSpent());
        statement.bindLong(8, entity.getSubmittedAt());
        statement.bindLong(9, entity.getStartedAt());
      }
    };
    this.__deletionAdapterOfTestSubmission = new EntityDeletionOrUpdateAdapter<TestSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `test_submissions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TestSubmission entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTestSubmission = new EntityDeletionOrUpdateAdapter<TestSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `test_submissions` SET `id` = ?,`studentId` = ?,`testPaperId` = ?,`answers` = ?,`score` = ?,`totalPoints` = ?,`timeSpent` = ?,`submittedAt` = ?,`startedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TestSubmission entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getTestPaperId());
        final String _tmp = __typeConverters.fromLongStringMap(entity.getAnswers());
        statement.bindString(4, _tmp);
        statement.bindLong(5, entity.getScore());
        statement.bindLong(6, entity.getTotalPoints());
        statement.bindLong(7, entity.getTimeSpent());
        statement.bindLong(8, entity.getSubmittedAt());
        statement.bindLong(9, entity.getStartedAt());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertSubmission(final TestSubmission submission,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTestSubmission.insertAndReturnId(submission);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSubmission(final TestSubmission submission,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTestSubmission.handle(submission);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSubmission(final TestSubmission submission,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTestSubmission.handle(submission);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSubmissionById(final long id,
      final Continuation<? super TestSubmission> $completion) {
    final String _sql = "SELECT * FROM test_submissions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TestSubmission>() {
      @Override
      @Nullable
      public TestSubmission call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTestPaperId = CursorUtil.getColumnIndexOrThrow(_cursor, "testPaperId");
          final int _cursorIndexOfAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "answers");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfTotalPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPoints");
          final int _cursorIndexOfTimeSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSpent");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final TestSubmission _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpTestPaperId;
            _tmpTestPaperId = _cursor.getLong(_cursorIndexOfTestPaperId);
            final Map<Long, String> _tmpAnswers;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfAnswers);
            _tmpAnswers = __typeConverters.toLongStringMap(_tmp);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpTotalPoints;
            _tmpTotalPoints = _cursor.getInt(_cursorIndexOfTotalPoints);
            final int _tmpTimeSpent;
            _tmpTimeSpent = _cursor.getInt(_cursorIndexOfTimeSpent);
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _result = new TestSubmission(_tmpId,_tmpStudentId,_tmpTestPaperId,_tmpAnswers,_tmpScore,_tmpTotalPoints,_tmpTimeSpent,_tmpSubmittedAt,_tmpStartedAt);
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
  public Flow<List<TestSubmission>> getSubmissionsByStudent(final long studentId) {
    final String _sql = "SELECT * FROM test_submissions WHERE studentId = ? ORDER BY submittedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"test_submissions"}, new Callable<List<TestSubmission>>() {
      @Override
      @NonNull
      public List<TestSubmission> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTestPaperId = CursorUtil.getColumnIndexOrThrow(_cursor, "testPaperId");
          final int _cursorIndexOfAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "answers");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfTotalPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPoints");
          final int _cursorIndexOfTimeSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSpent");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<TestSubmission> _result = new ArrayList<TestSubmission>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TestSubmission _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpTestPaperId;
            _tmpTestPaperId = _cursor.getLong(_cursorIndexOfTestPaperId);
            final Map<Long, String> _tmpAnswers;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfAnswers);
            _tmpAnswers = __typeConverters.toLongStringMap(_tmp);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpTotalPoints;
            _tmpTotalPoints = _cursor.getInt(_cursorIndexOfTotalPoints);
            final int _tmpTimeSpent;
            _tmpTimeSpent = _cursor.getInt(_cursorIndexOfTimeSpent);
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new TestSubmission(_tmpId,_tmpStudentId,_tmpTestPaperId,_tmpAnswers,_tmpScore,_tmpTotalPoints,_tmpTimeSpent,_tmpSubmittedAt,_tmpStartedAt);
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
  public Flow<List<TestSubmission>> getSubmissionsByTestPaper(final long testPaperId) {
    final String _sql = "SELECT * FROM test_submissions WHERE testPaperId = ? ORDER BY submittedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, testPaperId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"test_submissions"}, new Callable<List<TestSubmission>>() {
      @Override
      @NonNull
      public List<TestSubmission> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTestPaperId = CursorUtil.getColumnIndexOrThrow(_cursor, "testPaperId");
          final int _cursorIndexOfAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "answers");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfTotalPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPoints");
          final int _cursorIndexOfTimeSpent = CursorUtil.getColumnIndexOrThrow(_cursor, "timeSpent");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfStartedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAt");
          final List<TestSubmission> _result = new ArrayList<TestSubmission>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TestSubmission _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpTestPaperId;
            _tmpTestPaperId = _cursor.getLong(_cursorIndexOfTestPaperId);
            final Map<Long, String> _tmpAnswers;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfAnswers);
            _tmpAnswers = __typeConverters.toLongStringMap(_tmp);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpTotalPoints;
            _tmpTotalPoints = _cursor.getInt(_cursorIndexOfTotalPoints);
            final int _tmpTimeSpent;
            _tmpTimeSpent = _cursor.getInt(_cursorIndexOfTimeSpent);
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final long _tmpStartedAt;
            _tmpStartedAt = _cursor.getLong(_cursorIndexOfStartedAt);
            _item = new TestSubmission(_tmpId,_tmpStudentId,_tmpTestPaperId,_tmpAnswers,_tmpScore,_tmpTotalPoints,_tmpTimeSpent,_tmpSubmittedAt,_tmpStartedAt);
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

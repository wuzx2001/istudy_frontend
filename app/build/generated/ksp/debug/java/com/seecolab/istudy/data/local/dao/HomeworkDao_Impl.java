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
import com.seecolab.istudy.data.model.AnalysisResult;
import com.seecolab.istudy.data.model.HomeworkSubmission;
import com.seecolab.istudy.data.model.Subject;
import com.seecolab.istudy.data.model.SubmissionStatus;
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
public final class HomeworkDao_Impl implements HomeworkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HomeworkSubmission> __insertionAdapterOfHomeworkSubmission;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<HomeworkSubmission> __deletionAdapterOfHomeworkSubmission;

  private final EntityDeletionOrUpdateAdapter<HomeworkSubmission> __updateAdapterOfHomeworkSubmission;

  public HomeworkDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHomeworkSubmission = new EntityInsertionAdapter<HomeworkSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `homework_submissions` (`id`,`studentId`,`subject`,`imagePath`,`analysisResult`,`aiRecommendations`,`generatedTestPaperId`,`status`,`submittedAt`,`analyzedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HomeworkSubmission entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindString(3, __Subject_enumToString(entity.getSubject()));
        statement.bindString(4, entity.getImagePath());
        final String _tmp = __typeConverters.fromAnalysisResult(entity.getAnalysisResult());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        final String _tmp_1 = __typeConverters.fromStringList(entity.getAiRecommendations());
        statement.bindString(6, _tmp_1);
        if (entity.getGeneratedTestPaperId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getGeneratedTestPaperId());
        }
        statement.bindString(8, __SubmissionStatus_enumToString(entity.getStatus()));
        statement.bindLong(9, entity.getSubmittedAt());
        if (entity.getAnalyzedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getAnalyzedAt());
        }
      }
    };
    this.__deletionAdapterOfHomeworkSubmission = new EntityDeletionOrUpdateAdapter<HomeworkSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `homework_submissions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HomeworkSubmission entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfHomeworkSubmission = new EntityDeletionOrUpdateAdapter<HomeworkSubmission>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `homework_submissions` SET `id` = ?,`studentId` = ?,`subject` = ?,`imagePath` = ?,`analysisResult` = ?,`aiRecommendations` = ?,`generatedTestPaperId` = ?,`status` = ?,`submittedAt` = ?,`analyzedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HomeworkSubmission entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindString(3, __Subject_enumToString(entity.getSubject()));
        statement.bindString(4, entity.getImagePath());
        final String _tmp = __typeConverters.fromAnalysisResult(entity.getAnalysisResult());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        final String _tmp_1 = __typeConverters.fromStringList(entity.getAiRecommendations());
        statement.bindString(6, _tmp_1);
        if (entity.getGeneratedTestPaperId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getGeneratedTestPaperId());
        }
        statement.bindString(8, __SubmissionStatus_enumToString(entity.getStatus()));
        statement.bindLong(9, entity.getSubmittedAt());
        if (entity.getAnalyzedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getAnalyzedAt());
        }
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insertHomework(final HomeworkSubmission homework,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHomeworkSubmission.insertAndReturnId(homework);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHomework(final HomeworkSubmission homework,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHomeworkSubmission.handle(homework);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateHomework(final HomeworkSubmission homework,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHomeworkSubmission.handle(homework);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getHomeworkById(final long id,
      final Continuation<? super HomeworkSubmission> $completion) {
    final String _sql = "SELECT * FROM homework_submissions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HomeworkSubmission>() {
      @Override
      @Nullable
      public HomeworkSubmission call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfAnalysisResult = CursorUtil.getColumnIndexOrThrow(_cursor, "analysisResult");
          final int _cursorIndexOfAiRecommendations = CursorUtil.getColumnIndexOrThrow(_cursor, "aiRecommendations");
          final int _cursorIndexOfGeneratedTestPaperId = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedTestPaperId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfAnalyzedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "analyzedAt");
          final HomeworkSubmission _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final AnalysisResult _tmpAnalysisResult;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAnalysisResult)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAnalysisResult);
            }
            _tmpAnalysisResult = __typeConverters.toAnalysisResult(_tmp);
            final List<String> _tmpAiRecommendations;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfAiRecommendations);
            _tmpAiRecommendations = __typeConverters.toStringList(_tmp_1);
            final Long _tmpGeneratedTestPaperId;
            if (_cursor.isNull(_cursorIndexOfGeneratedTestPaperId)) {
              _tmpGeneratedTestPaperId = null;
            } else {
              _tmpGeneratedTestPaperId = _cursor.getLong(_cursorIndexOfGeneratedTestPaperId);
            }
            final SubmissionStatus _tmpStatus;
            _tmpStatus = __SubmissionStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpAnalyzedAt;
            if (_cursor.isNull(_cursorIndexOfAnalyzedAt)) {
              _tmpAnalyzedAt = null;
            } else {
              _tmpAnalyzedAt = _cursor.getLong(_cursorIndexOfAnalyzedAt);
            }
            _result = new HomeworkSubmission(_tmpId,_tmpStudentId,_tmpSubject,_tmpImagePath,_tmpAnalysisResult,_tmpAiRecommendations,_tmpGeneratedTestPaperId,_tmpStatus,_tmpSubmittedAt,_tmpAnalyzedAt);
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
  public Flow<List<HomeworkSubmission>> getHomeworkByStudent(final long studentId) {
    final String _sql = "SELECT * FROM homework_submissions WHERE studentId = ? ORDER BY submittedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"homework_submissions"}, new Callable<List<HomeworkSubmission>>() {
      @Override
      @NonNull
      public List<HomeworkSubmission> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfAnalysisResult = CursorUtil.getColumnIndexOrThrow(_cursor, "analysisResult");
          final int _cursorIndexOfAiRecommendations = CursorUtil.getColumnIndexOrThrow(_cursor, "aiRecommendations");
          final int _cursorIndexOfGeneratedTestPaperId = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedTestPaperId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfAnalyzedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "analyzedAt");
          final List<HomeworkSubmission> _result = new ArrayList<HomeworkSubmission>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HomeworkSubmission _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final AnalysisResult _tmpAnalysisResult;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAnalysisResult)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAnalysisResult);
            }
            _tmpAnalysisResult = __typeConverters.toAnalysisResult(_tmp);
            final List<String> _tmpAiRecommendations;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfAiRecommendations);
            _tmpAiRecommendations = __typeConverters.toStringList(_tmp_1);
            final Long _tmpGeneratedTestPaperId;
            if (_cursor.isNull(_cursorIndexOfGeneratedTestPaperId)) {
              _tmpGeneratedTestPaperId = null;
            } else {
              _tmpGeneratedTestPaperId = _cursor.getLong(_cursorIndexOfGeneratedTestPaperId);
            }
            final SubmissionStatus _tmpStatus;
            _tmpStatus = __SubmissionStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpAnalyzedAt;
            if (_cursor.isNull(_cursorIndexOfAnalyzedAt)) {
              _tmpAnalyzedAt = null;
            } else {
              _tmpAnalyzedAt = _cursor.getLong(_cursorIndexOfAnalyzedAt);
            }
            _item = new HomeworkSubmission(_tmpId,_tmpStudentId,_tmpSubject,_tmpImagePath,_tmpAnalysisResult,_tmpAiRecommendations,_tmpGeneratedTestPaperId,_tmpStatus,_tmpSubmittedAt,_tmpAnalyzedAt);
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
  public Flow<List<HomeworkSubmission>> getHomeworkByStatus(final SubmissionStatus status) {
    final String _sql = "SELECT * FROM homework_submissions WHERE status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __SubmissionStatus_enumToString(status));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"homework_submissions"}, new Callable<List<HomeworkSubmission>>() {
      @Override
      @NonNull
      public List<HomeworkSubmission> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfAnalysisResult = CursorUtil.getColumnIndexOrThrow(_cursor, "analysisResult");
          final int _cursorIndexOfAiRecommendations = CursorUtil.getColumnIndexOrThrow(_cursor, "aiRecommendations");
          final int _cursorIndexOfGeneratedTestPaperId = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedTestPaperId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final int _cursorIndexOfAnalyzedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "analyzedAt");
          final List<HomeworkSubmission> _result = new ArrayList<HomeworkSubmission>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HomeworkSubmission _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final AnalysisResult _tmpAnalysisResult;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfAnalysisResult)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfAnalysisResult);
            }
            _tmpAnalysisResult = __typeConverters.toAnalysisResult(_tmp);
            final List<String> _tmpAiRecommendations;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfAiRecommendations);
            _tmpAiRecommendations = __typeConverters.toStringList(_tmp_1);
            final Long _tmpGeneratedTestPaperId;
            if (_cursor.isNull(_cursorIndexOfGeneratedTestPaperId)) {
              _tmpGeneratedTestPaperId = null;
            } else {
              _tmpGeneratedTestPaperId = _cursor.getLong(_cursorIndexOfGeneratedTestPaperId);
            }
            final SubmissionStatus _tmpStatus;
            _tmpStatus = __SubmissionStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            final Long _tmpAnalyzedAt;
            if (_cursor.isNull(_cursorIndexOfAnalyzedAt)) {
              _tmpAnalyzedAt = null;
            } else {
              _tmpAnalyzedAt = _cursor.getLong(_cursorIndexOfAnalyzedAt);
            }
            _item = new HomeworkSubmission(_tmpId,_tmpStudentId,_tmpSubject,_tmpImagePath,_tmpAnalysisResult,_tmpAiRecommendations,_tmpGeneratedTestPaperId,_tmpStatus,_tmpSubmittedAt,_tmpAnalyzedAt);
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

  private String __SubmissionStatus_enumToString(@NonNull final SubmissionStatus _value) {
    switch (_value) {
      case PENDING: return "PENDING";
      case ANALYZING: return "ANALYZING";
      case COMPLETED: return "COMPLETED";
      case FAILED: return "FAILED";
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

  private SubmissionStatus __SubmissionStatus_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "PENDING": return SubmissionStatus.PENDING;
      case "ANALYZING": return SubmissionStatus.ANALYZING;
      case "COMPLETED": return SubmissionStatus.COMPLETED;
      case "FAILED": return SubmissionStatus.FAILED;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

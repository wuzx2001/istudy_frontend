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
import com.seecolab.istudy.data.model.LearningReport;
import com.seecolab.istudy.data.model.Subject;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
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
public final class LearningReportDao_Impl implements LearningReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LearningReport> __insertionAdapterOfLearningReport;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<LearningReport> __deletionAdapterOfLearningReport;

  private final EntityDeletionOrUpdateAdapter<LearningReport> __updateAdapterOfLearningReport;

  public LearningReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLearningReport = new EntityInsertionAdapter<LearningReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `learning_reports` (`id`,`studentId`,`reportPeriodStart`,`reportPeriodEnd`,`overallProgress`,`subjectScores`,`completedTests`,`totalStudyTime`,`strengthAreas`,`improvementAreas`,`recommendations`,`generatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LearningReport entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getReportPeriodStart());
        statement.bindLong(4, entity.getReportPeriodEnd());
        statement.bindDouble(5, entity.getOverallProgress());
        final String _tmp = __typeConverters.fromSubjectFloatMap(entity.getSubjectScores());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getCompletedTests());
        statement.bindLong(8, entity.getTotalStudyTime());
        final String _tmp_1 = __typeConverters.fromStringList(entity.getStrengthAreas());
        statement.bindString(9, _tmp_1);
        final String _tmp_2 = __typeConverters.fromStringList(entity.getImprovementAreas());
        statement.bindString(10, _tmp_2);
        final String _tmp_3 = __typeConverters.fromStringList(entity.getRecommendations());
        statement.bindString(11, _tmp_3);
        statement.bindLong(12, entity.getGeneratedAt());
      }
    };
    this.__deletionAdapterOfLearningReport = new EntityDeletionOrUpdateAdapter<LearningReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `learning_reports` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LearningReport entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfLearningReport = new EntityDeletionOrUpdateAdapter<LearningReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `learning_reports` SET `id` = ?,`studentId` = ?,`reportPeriodStart` = ?,`reportPeriodEnd` = ?,`overallProgress` = ?,`subjectScores` = ?,`completedTests` = ?,`totalStudyTime` = ?,`strengthAreas` = ?,`improvementAreas` = ?,`recommendations` = ?,`generatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LearningReport entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getReportPeriodStart());
        statement.bindLong(4, entity.getReportPeriodEnd());
        statement.bindDouble(5, entity.getOverallProgress());
        final String _tmp = __typeConverters.fromSubjectFloatMap(entity.getSubjectScores());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getCompletedTests());
        statement.bindLong(8, entity.getTotalStudyTime());
        final String _tmp_1 = __typeConverters.fromStringList(entity.getStrengthAreas());
        statement.bindString(9, _tmp_1);
        final String _tmp_2 = __typeConverters.fromStringList(entity.getImprovementAreas());
        statement.bindString(10, _tmp_2);
        final String _tmp_3 = __typeConverters.fromStringList(entity.getRecommendations());
        statement.bindString(11, _tmp_3);
        statement.bindLong(12, entity.getGeneratedAt());
        statement.bindLong(13, entity.getId());
      }
    };
  }

  @Override
  public Object insertReport(final LearningReport report,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLearningReport.insertAndReturnId(report);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReport(final LearningReport report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfLearningReport.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReport(final LearningReport report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLearningReport.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getReportById(final long id,
      final Continuation<? super LearningReport> $completion) {
    final String _sql = "SELECT * FROM learning_reports WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<LearningReport>() {
      @Override
      @Nullable
      public LearningReport call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfReportPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "reportPeriodStart");
          final int _cursorIndexOfReportPeriodEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "reportPeriodEnd");
          final int _cursorIndexOfOverallProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "overallProgress");
          final int _cursorIndexOfSubjectScores = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectScores");
          final int _cursorIndexOfCompletedTests = CursorUtil.getColumnIndexOrThrow(_cursor, "completedTests");
          final int _cursorIndexOfTotalStudyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStudyTime");
          final int _cursorIndexOfStrengthAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "strengthAreas");
          final int _cursorIndexOfImprovementAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "improvementAreas");
          final int _cursorIndexOfRecommendations = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendations");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final LearningReport _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpReportPeriodStart;
            _tmpReportPeriodStart = _cursor.getLong(_cursorIndexOfReportPeriodStart);
            final long _tmpReportPeriodEnd;
            _tmpReportPeriodEnd = _cursor.getLong(_cursorIndexOfReportPeriodEnd);
            final float _tmpOverallProgress;
            _tmpOverallProgress = _cursor.getFloat(_cursorIndexOfOverallProgress);
            final Map<Subject, Float> _tmpSubjectScores;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjectScores);
            _tmpSubjectScores = __typeConverters.toSubjectFloatMap(_tmp);
            final int _tmpCompletedTests;
            _tmpCompletedTests = _cursor.getInt(_cursorIndexOfCompletedTests);
            final int _tmpTotalStudyTime;
            _tmpTotalStudyTime = _cursor.getInt(_cursorIndexOfTotalStudyTime);
            final List<String> _tmpStrengthAreas;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrengthAreas);
            _tmpStrengthAreas = __typeConverters.toStringList(_tmp_1);
            final List<String> _tmpImprovementAreas;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImprovementAreas);
            _tmpImprovementAreas = __typeConverters.toStringList(_tmp_2);
            final List<String> _tmpRecommendations;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfRecommendations);
            _tmpRecommendations = __typeConverters.toStringList(_tmp_3);
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            _result = new LearningReport(_tmpId,_tmpStudentId,_tmpReportPeriodStart,_tmpReportPeriodEnd,_tmpOverallProgress,_tmpSubjectScores,_tmpCompletedTests,_tmpTotalStudyTime,_tmpStrengthAreas,_tmpImprovementAreas,_tmpRecommendations,_tmpGeneratedAt);
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
  public Flow<List<LearningReport>> getReportsByStudent(final long studentId) {
    final String _sql = "SELECT * FROM learning_reports WHERE studentId = ? ORDER BY generatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"learning_reports"}, new Callable<List<LearningReport>>() {
      @Override
      @NonNull
      public List<LearningReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfReportPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "reportPeriodStart");
          final int _cursorIndexOfReportPeriodEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "reportPeriodEnd");
          final int _cursorIndexOfOverallProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "overallProgress");
          final int _cursorIndexOfSubjectScores = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectScores");
          final int _cursorIndexOfCompletedTests = CursorUtil.getColumnIndexOrThrow(_cursor, "completedTests");
          final int _cursorIndexOfTotalStudyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStudyTime");
          final int _cursorIndexOfStrengthAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "strengthAreas");
          final int _cursorIndexOfImprovementAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "improvementAreas");
          final int _cursorIndexOfRecommendations = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendations");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final List<LearningReport> _result = new ArrayList<LearningReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LearningReport _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpReportPeriodStart;
            _tmpReportPeriodStart = _cursor.getLong(_cursorIndexOfReportPeriodStart);
            final long _tmpReportPeriodEnd;
            _tmpReportPeriodEnd = _cursor.getLong(_cursorIndexOfReportPeriodEnd);
            final float _tmpOverallProgress;
            _tmpOverallProgress = _cursor.getFloat(_cursorIndexOfOverallProgress);
            final Map<Subject, Float> _tmpSubjectScores;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjectScores);
            _tmpSubjectScores = __typeConverters.toSubjectFloatMap(_tmp);
            final int _tmpCompletedTests;
            _tmpCompletedTests = _cursor.getInt(_cursorIndexOfCompletedTests);
            final int _tmpTotalStudyTime;
            _tmpTotalStudyTime = _cursor.getInt(_cursorIndexOfTotalStudyTime);
            final List<String> _tmpStrengthAreas;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrengthAreas);
            _tmpStrengthAreas = __typeConverters.toStringList(_tmp_1);
            final List<String> _tmpImprovementAreas;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImprovementAreas);
            _tmpImprovementAreas = __typeConverters.toStringList(_tmp_2);
            final List<String> _tmpRecommendations;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfRecommendations);
            _tmpRecommendations = __typeConverters.toStringList(_tmp_3);
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            _item = new LearningReport(_tmpId,_tmpStudentId,_tmpReportPeriodStart,_tmpReportPeriodEnd,_tmpOverallProgress,_tmpSubjectScores,_tmpCompletedTests,_tmpTotalStudyTime,_tmpStrengthAreas,_tmpImprovementAreas,_tmpRecommendations,_tmpGeneratedAt);
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
  public Object getLatestReport(final long studentId,
      final Continuation<? super LearningReport> $completion) {
    final String _sql = "SELECT * FROM learning_reports WHERE studentId = ? ORDER BY generatedAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<LearningReport>() {
      @Override
      @Nullable
      public LearningReport call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfReportPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "reportPeriodStart");
          final int _cursorIndexOfReportPeriodEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "reportPeriodEnd");
          final int _cursorIndexOfOverallProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "overallProgress");
          final int _cursorIndexOfSubjectScores = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectScores");
          final int _cursorIndexOfCompletedTests = CursorUtil.getColumnIndexOrThrow(_cursor, "completedTests");
          final int _cursorIndexOfTotalStudyTime = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStudyTime");
          final int _cursorIndexOfStrengthAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "strengthAreas");
          final int _cursorIndexOfImprovementAreas = CursorUtil.getColumnIndexOrThrow(_cursor, "improvementAreas");
          final int _cursorIndexOfRecommendations = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendations");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final LearningReport _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpReportPeriodStart;
            _tmpReportPeriodStart = _cursor.getLong(_cursorIndexOfReportPeriodStart);
            final long _tmpReportPeriodEnd;
            _tmpReportPeriodEnd = _cursor.getLong(_cursorIndexOfReportPeriodEnd);
            final float _tmpOverallProgress;
            _tmpOverallProgress = _cursor.getFloat(_cursorIndexOfOverallProgress);
            final Map<Subject, Float> _tmpSubjectScores;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjectScores);
            _tmpSubjectScores = __typeConverters.toSubjectFloatMap(_tmp);
            final int _tmpCompletedTests;
            _tmpCompletedTests = _cursor.getInt(_cursorIndexOfCompletedTests);
            final int _tmpTotalStudyTime;
            _tmpTotalStudyTime = _cursor.getInt(_cursorIndexOfTotalStudyTime);
            final List<String> _tmpStrengthAreas;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStrengthAreas);
            _tmpStrengthAreas = __typeConverters.toStringList(_tmp_1);
            final List<String> _tmpImprovementAreas;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfImprovementAreas);
            _tmpImprovementAreas = __typeConverters.toStringList(_tmp_2);
            final List<String> _tmpRecommendations;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfRecommendations);
            _tmpRecommendations = __typeConverters.toStringList(_tmp_3);
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            _result = new LearningReport(_tmpId,_tmpStudentId,_tmpReportPeriodStart,_tmpReportPeriodEnd,_tmpOverallProgress,_tmpSubjectScores,_tmpCompletedTests,_tmpTotalStudyTime,_tmpStrengthAreas,_tmpImprovementAreas,_tmpRecommendations,_tmpGeneratedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

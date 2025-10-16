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
import com.seecolab.istudy.data.model.BookingStatus;
import com.seecolab.istudy.data.model.Subject;
import com.seecolab.istudy.data.model.TeacherBooking;
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
public final class TeacherBookingDao_Impl implements TeacherBookingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TeacherBooking> __insertionAdapterOfTeacherBooking;

  private final EntityDeletionOrUpdateAdapter<TeacherBooking> __deletionAdapterOfTeacherBooking;

  private final EntityDeletionOrUpdateAdapter<TeacherBooking> __updateAdapterOfTeacherBooking;

  public TeacherBookingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTeacherBooking = new EntityInsertionAdapter<TeacherBooking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `teacher_bookings` (`id`,`studentId`,`teacherId`,`subject`,`scheduledTime`,`duration`,`status`,`notes`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TeacherBooking entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getTeacherId());
        statement.bindString(4, __Subject_enumToString(entity.getSubject()));
        statement.bindLong(5, entity.getScheduledTime());
        statement.bindLong(6, entity.getDuration());
        statement.bindString(7, __BookingStatus_enumToString(entity.getStatus()));
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfTeacherBooking = new EntityDeletionOrUpdateAdapter<TeacherBooking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `teacher_bookings` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TeacherBooking entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTeacherBooking = new EntityDeletionOrUpdateAdapter<TeacherBooking>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `teacher_bookings` SET `id` = ?,`studentId` = ?,`teacherId` = ?,`subject` = ?,`scheduledTime` = ?,`duration` = ?,`status` = ?,`notes` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TeacherBooking entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStudentId());
        statement.bindLong(3, entity.getTeacherId());
        statement.bindString(4, __Subject_enumToString(entity.getSubject()));
        statement.bindLong(5, entity.getScheduledTime());
        statement.bindLong(6, entity.getDuration());
        statement.bindString(7, __BookingStatus_enumToString(entity.getStatus()));
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertBooking(final TeacherBooking booking,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTeacherBooking.insertAndReturnId(booking);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBooking(final TeacherBooking booking,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTeacherBooking.handle(booking);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBooking(final TeacherBooking booking,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTeacherBooking.handle(booking);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getBookingById(final long id,
      final Continuation<? super TeacherBooking> $completion) {
    final String _sql = "SELECT * FROM teacher_bookings WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TeacherBooking>() {
      @Override
      @Nullable
      public TeacherBooking call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTeacherId = CursorUtil.getColumnIndexOrThrow(_cursor, "teacherId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final TeacherBooking _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpTeacherId;
            _tmpTeacherId = _cursor.getLong(_cursorIndexOfTeacherId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final long _tmpScheduledTime;
            _tmpScheduledTime = _cursor.getLong(_cursorIndexOfScheduledTime);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final BookingStatus _tmpStatus;
            _tmpStatus = __BookingStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new TeacherBooking(_tmpId,_tmpStudentId,_tmpTeacherId,_tmpSubject,_tmpScheduledTime,_tmpDuration,_tmpStatus,_tmpNotes,_tmpCreatedAt);
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
  public Flow<List<TeacherBooking>> getBookingsByStudent(final long studentId) {
    final String _sql = "SELECT * FROM teacher_bookings WHERE studentId = ? ORDER BY scheduledTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"teacher_bookings"}, new Callable<List<TeacherBooking>>() {
      @Override
      @NonNull
      public List<TeacherBooking> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTeacherId = CursorUtil.getColumnIndexOrThrow(_cursor, "teacherId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<TeacherBooking> _result = new ArrayList<TeacherBooking>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TeacherBooking _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpTeacherId;
            _tmpTeacherId = _cursor.getLong(_cursorIndexOfTeacherId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final long _tmpScheduledTime;
            _tmpScheduledTime = _cursor.getLong(_cursorIndexOfScheduledTime);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final BookingStatus _tmpStatus;
            _tmpStatus = __BookingStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new TeacherBooking(_tmpId,_tmpStudentId,_tmpTeacherId,_tmpSubject,_tmpScheduledTime,_tmpDuration,_tmpStatus,_tmpNotes,_tmpCreatedAt);
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
  public Flow<List<TeacherBooking>> getBookingsByTeacher(final long teacherId) {
    final String _sql = "SELECT * FROM teacher_bookings WHERE teacherId = ? ORDER BY scheduledTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, teacherId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"teacher_bookings"}, new Callable<List<TeacherBooking>>() {
      @Override
      @NonNull
      public List<TeacherBooking> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfTeacherId = CursorUtil.getColumnIndexOrThrow(_cursor, "teacherId");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfScheduledTime = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<TeacherBooking> _result = new ArrayList<TeacherBooking>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TeacherBooking _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStudentId;
            _tmpStudentId = _cursor.getLong(_cursorIndexOfStudentId);
            final long _tmpTeacherId;
            _tmpTeacherId = _cursor.getLong(_cursorIndexOfTeacherId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final long _tmpScheduledTime;
            _tmpScheduledTime = _cursor.getLong(_cursorIndexOfScheduledTime);
            final int _tmpDuration;
            _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            final BookingStatus _tmpStatus;
            _tmpStatus = __BookingStatus_stringToEnum(_cursor.getString(_cursorIndexOfStatus));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new TeacherBooking(_tmpId,_tmpStudentId,_tmpTeacherId,_tmpSubject,_tmpScheduledTime,_tmpDuration,_tmpStatus,_tmpNotes,_tmpCreatedAt);
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

  private String __BookingStatus_enumToString(@NonNull final BookingStatus _value) {
    switch (_value) {
      case PENDING: return "PENDING";
      case CONFIRMED: return "CONFIRMED";
      case COMPLETED: return "COMPLETED";
      case CANCELLED: return "CANCELLED";
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

  private BookingStatus __BookingStatus_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "PENDING": return BookingStatus.PENDING;
      case "CONFIRMED": return BookingStatus.CONFIRMED;
      case "COMPLETED": return BookingStatus.COMPLETED;
      case "CANCELLED": return BookingStatus.CANCELLED;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

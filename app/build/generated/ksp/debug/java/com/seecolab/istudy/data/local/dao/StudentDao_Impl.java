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
import com.seecolab.istudy.data.model.Gender;
import com.seecolab.istudy.data.model.Grade;
import com.seecolab.istudy.data.model.Student;
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
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Student> __insertionAdapterOfStudent;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<Student> __deletionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __updateAdapterOfStudent;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `students` (`id`,`name`,`age`,`gender`,`grade`,`subjectPreferences`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getAge());
        statement.bindString(4, __Gender_enumToString(entity.getGender()));
        statement.bindString(5, __Grade_enumToString(entity.getGrade()));
        final String _tmp = __typeConverters.fromSubjectList(entity.getSubjectPreferences());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `students` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `students` SET `id` = ?,`name` = ?,`age` = ?,`gender` = ?,`grade` = ?,`subjectPreferences` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getAge());
        statement.bindString(4, __Gender_enumToString(entity.getGender()));
        statement.bindString(5, __Grade_enumToString(entity.getGrade()));
        final String _tmp = __typeConverters.fromSubjectList(entity.getSubjectPreferences());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insertStudent(final Student student, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStudent.insertAndReturnId(student);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteStudent(final Student student, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStudent.handle(student);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStudent(final Student student, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStudent.handle(student);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getStudentById(final long id, final Continuation<? super Student> $completion) {
    final String _sql = "SELECT * FROM students WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfSubjectPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectPreferences");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Student _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final Gender _tmpGender;
            _tmpGender = __Gender_stringToEnum(_cursor.getString(_cursorIndexOfGender));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final List<Subject> _tmpSubjectPreferences;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjectPreferences);
            _tmpSubjectPreferences = __typeConverters.toSubjectList(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new Student(_tmpId,_tmpName,_tmpAge,_tmpGender,_tmpGrade,_tmpSubjectPreferences,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Student>> getAllStudents() {
    final String _sql = "SELECT * FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"students"}, new Callable<List<Student>>() {
      @Override
      @NonNull
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfSubjectPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectPreferences");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final Gender _tmpGender;
            _tmpGender = __Gender_stringToEnum(_cursor.getString(_cursorIndexOfGender));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final List<Subject> _tmpSubjectPreferences;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjectPreferences);
            _tmpSubjectPreferences = __typeConverters.toSubjectList(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Student(_tmpId,_tmpName,_tmpAge,_tmpGender,_tmpGrade,_tmpSubjectPreferences,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getCurrentStudent(final Continuation<? super Student> $completion) {
    final String _sql = "SELECT * FROM students ORDER BY createdAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfSubjectPreferences = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectPreferences");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Student _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final Gender _tmpGender;
            _tmpGender = __Gender_stringToEnum(_cursor.getString(_cursorIndexOfGender));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final List<Subject> _tmpSubjectPreferences;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjectPreferences);
            _tmpSubjectPreferences = __typeConverters.toSubjectList(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new Student(_tmpId,_tmpName,_tmpAge,_tmpGender,_tmpGrade,_tmpSubjectPreferences,_tmpCreatedAt,_tmpUpdatedAt);
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

  private String __Gender_enumToString(@NonNull final Gender _value) {
    switch (_value) {
      case MALE: return "MALE";
      case FEMALE: return "FEMALE";
      case UNKNOWN: return "UNKNOWN";
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

  private Gender __Gender_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "MALE": return Gender.MALE;
      case "FEMALE": return Gender.FEMALE;
      case "UNKNOWN": return Gender.UNKNOWN;
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
}

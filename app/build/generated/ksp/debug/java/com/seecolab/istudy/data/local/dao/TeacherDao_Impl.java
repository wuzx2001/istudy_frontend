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
import com.seecolab.istudy.data.model.Subject;
import com.seecolab.istudy.data.model.Teacher;
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
public final class TeacherDao_Impl implements TeacherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Teacher> __insertionAdapterOfTeacher;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<Teacher> __deletionAdapterOfTeacher;

  private final EntityDeletionOrUpdateAdapter<Teacher> __updateAdapterOfTeacher;

  public TeacherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTeacher = new EntityInsertionAdapter<Teacher>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `teachers` (`id`,`name`,`gender`,`age`,`subjects`,`location`,`experience`,`rating`,`hourlyRate`,`description`,`avatar`,`isAvailable`,`qualifications`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Teacher entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, __Gender_enumToString(entity.getGender()));
        statement.bindLong(4, entity.getAge());
        final String _tmp = __typeConverters.fromSubjectList(entity.getSubjects());
        statement.bindString(5, _tmp);
        statement.bindString(6, entity.getLocation());
        statement.bindLong(7, entity.getExperience());
        statement.bindDouble(8, entity.getRating());
        statement.bindDouble(9, entity.getHourlyRate());
        statement.bindString(10, entity.getDescription());
        if (entity.getAvatar() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAvatar());
        }
        final int _tmp_1 = entity.isAvailable() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        final String _tmp_2 = __typeConverters.fromStringList(entity.getQualifications());
        statement.bindString(13, _tmp_2);
        statement.bindLong(14, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfTeacher = new EntityDeletionOrUpdateAdapter<Teacher>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `teachers` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Teacher entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTeacher = new EntityDeletionOrUpdateAdapter<Teacher>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `teachers` SET `id` = ?,`name` = ?,`gender` = ?,`age` = ?,`subjects` = ?,`location` = ?,`experience` = ?,`rating` = ?,`hourlyRate` = ?,`description` = ?,`avatar` = ?,`isAvailable` = ?,`qualifications` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Teacher entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, __Gender_enumToString(entity.getGender()));
        statement.bindLong(4, entity.getAge());
        final String _tmp = __typeConverters.fromSubjectList(entity.getSubjects());
        statement.bindString(5, _tmp);
        statement.bindString(6, entity.getLocation());
        statement.bindLong(7, entity.getExperience());
        statement.bindDouble(8, entity.getRating());
        statement.bindDouble(9, entity.getHourlyRate());
        statement.bindString(10, entity.getDescription());
        if (entity.getAvatar() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAvatar());
        }
        final int _tmp_1 = entity.isAvailable() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        final String _tmp_2 = __typeConverters.fromStringList(entity.getQualifications());
        statement.bindString(13, _tmp_2);
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getId());
      }
    };
  }

  @Override
  public Object insertTeacher(final Teacher teacher, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTeacher.insertAndReturnId(teacher);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTeachers(final List<Teacher> teachers,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTeacher.insert(teachers);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTeacher(final Teacher teacher, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTeacher.handle(teacher);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTeacher(final Teacher teacher, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTeacher.handle(teacher);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTeacherById(final long id, final Continuation<? super Teacher> $completion) {
    final String _sql = "SELECT * FROM teachers WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Teacher>() {
      @Override
      @Nullable
      public Teacher call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfSubjects = CursorUtil.getColumnIndexOrThrow(_cursor, "subjects");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "experience");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfIsAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "isAvailable");
          final int _cursorIndexOfQualifications = CursorUtil.getColumnIndexOrThrow(_cursor, "qualifications");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Teacher _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Gender _tmpGender;
            _tmpGender = __Gender_stringToEnum(_cursor.getString(_cursorIndexOfGender));
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final List<Subject> _tmpSubjects;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjects);
            _tmpSubjects = __typeConverters.toSubjectList(_tmp);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpExperience;
            _tmpExperience = _cursor.getInt(_cursorIndexOfExperience);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final double _tmpHourlyRate;
            _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            final boolean _tmpIsAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAvailable);
            _tmpIsAvailable = _tmp_1 != 0;
            final List<String> _tmpQualifications;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfQualifications);
            _tmpQualifications = __typeConverters.toStringList(_tmp_2);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Teacher(_tmpId,_tmpName,_tmpGender,_tmpAge,_tmpSubjects,_tmpLocation,_tmpExperience,_tmpRating,_tmpHourlyRate,_tmpDescription,_tmpAvatar,_tmpIsAvailable,_tmpQualifications,_tmpCreatedAt);
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
  public Flow<List<Teacher>> getAvailableTeachers() {
    final String _sql = "SELECT * FROM teachers WHERE isAvailable = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"teachers"}, new Callable<List<Teacher>>() {
      @Override
      @NonNull
      public List<Teacher> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfSubjects = CursorUtil.getColumnIndexOrThrow(_cursor, "subjects");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "experience");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfIsAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "isAvailable");
          final int _cursorIndexOfQualifications = CursorUtil.getColumnIndexOrThrow(_cursor, "qualifications");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Teacher> _result = new ArrayList<Teacher>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Teacher _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Gender _tmpGender;
            _tmpGender = __Gender_stringToEnum(_cursor.getString(_cursorIndexOfGender));
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final List<Subject> _tmpSubjects;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjects);
            _tmpSubjects = __typeConverters.toSubjectList(_tmp);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpExperience;
            _tmpExperience = _cursor.getInt(_cursorIndexOfExperience);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final double _tmpHourlyRate;
            _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            final boolean _tmpIsAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAvailable);
            _tmpIsAvailable = _tmp_1 != 0;
            final List<String> _tmpQualifications;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfQualifications);
            _tmpQualifications = __typeConverters.toStringList(_tmp_2);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Teacher(_tmpId,_tmpName,_tmpGender,_tmpAge,_tmpSubjects,_tmpLocation,_tmpExperience,_tmpRating,_tmpHourlyRate,_tmpDescription,_tmpAvatar,_tmpIsAvailable,_tmpQualifications,_tmpCreatedAt);
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
  public Flow<List<Teacher>> getTeachersByLocation(final String location) {
    final String _sql = "SELECT * FROM teachers WHERE location LIKE '%' || ? || '%' AND isAvailable = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, location);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"teachers"}, new Callable<List<Teacher>>() {
      @Override
      @NonNull
      public List<Teacher> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfSubjects = CursorUtil.getColumnIndexOrThrow(_cursor, "subjects");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "experience");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfHourlyRate = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyRate");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfIsAvailable = CursorUtil.getColumnIndexOrThrow(_cursor, "isAvailable");
          final int _cursorIndexOfQualifications = CursorUtil.getColumnIndexOrThrow(_cursor, "qualifications");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Teacher> _result = new ArrayList<Teacher>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Teacher _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Gender _tmpGender;
            _tmpGender = __Gender_stringToEnum(_cursor.getString(_cursorIndexOfGender));
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final List<Subject> _tmpSubjects;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSubjects);
            _tmpSubjects = __typeConverters.toSubjectList(_tmp);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final int _tmpExperience;
            _tmpExperience = _cursor.getInt(_cursorIndexOfExperience);
            final float _tmpRating;
            _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
            final double _tmpHourlyRate;
            _tmpHourlyRate = _cursor.getDouble(_cursorIndexOfHourlyRate);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            final boolean _tmpIsAvailable;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAvailable);
            _tmpIsAvailable = _tmp_1 != 0;
            final List<String> _tmpQualifications;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfQualifications);
            _tmpQualifications = __typeConverters.toStringList(_tmp_2);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Teacher(_tmpId,_tmpName,_tmpGender,_tmpAge,_tmpSubjects,_tmpLocation,_tmpExperience,_tmpRating,_tmpHourlyRate,_tmpDescription,_tmpAvatar,_tmpIsAvailable,_tmpQualifications,_tmpCreatedAt);
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

  private String __Gender_enumToString(@NonNull final Gender _value) {
    switch (_value) {
      case MALE: return "MALE";
      case FEMALE: return "FEMALE";
      case UNKNOWN: return "UNKNOWN";
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
}

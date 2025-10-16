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
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.seecolab.istudy.data.model.User;
import com.seecolab.istudy.data.model.UserRole;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfLogoutAllUsers;

  private final SharedSQLiteStatement __preparedStmtOfLoginUser;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`phoneNumber`,`role`,`isLoggedIn`,`lastLoginTime`,`createdAt`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getPhoneNumber());
        statement.bindString(2, __UserRole_enumToString(entity.getRole()));
        final int _tmp = entity.isLoggedIn() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getLastLoginTime());
        statement.bindLong(5, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `users` WHERE `phoneNumber` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getPhoneNumber());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `phoneNumber` = ?,`role` = ?,`isLoggedIn` = ?,`lastLoginTime` = ?,`createdAt` = ? WHERE `phoneNumber` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getPhoneNumber());
        statement.bindString(2, __UserRole_enumToString(entity.getRole()));
        final int _tmp = entity.isLoggedIn() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getLastLoginTime());
        statement.bindLong(5, entity.getCreatedAt());
        statement.bindString(6, entity.getPhoneNumber());
      }
    };
    this.__preparedStmtOfLogoutAllUsers = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isLoggedIn = 0";
        return _query;
      }
    };
    this.__preparedStmtOfLoginUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isLoggedIn = 1, lastLoginTime = ? WHERE phoneNumber = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object logoutAllUsers(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfLogoutAllUsers.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfLogoutAllUsers.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object loginUser(final String phoneNumber, final long loginTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfLoginUser.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, loginTime);
        _argIndex = 2;
        _stmt.bindString(_argIndex, phoneNumber);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfLoginUser.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserByPhone(final String phoneNumber,
      final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE phoneNumber = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, phoneNumber);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isLoggedIn");
          final int _cursorIndexOfLastLoginTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLoginTime");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final UserRole _tmpRole;
            _tmpRole = __UserRole_stringToEnum(_cursor.getString(_cursorIndexOfRole));
            final boolean _tmpIsLoggedIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
            _tmpIsLoggedIn = _tmp != 0;
            final long _tmpLastLoginTime;
            _tmpLastLoginTime = _cursor.getLong(_cursorIndexOfLastLoginTime);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new User(_tmpPhoneNumber,_tmpRole,_tmpIsLoggedIn,_tmpLastLoginTime,_tmpCreatedAt);
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
  public Object getCurrentUser(final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isLoggedIn");
          final int _cursorIndexOfLastLoginTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLoginTime");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final UserRole _tmpRole;
            _tmpRole = __UserRole_stringToEnum(_cursor.getString(_cursorIndexOfRole));
            final boolean _tmpIsLoggedIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
            _tmpIsLoggedIn = _tmp != 0;
            final long _tmpLastLoginTime;
            _tmpLastLoginTime = _cursor.getLong(_cursorIndexOfLastLoginTime);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new User(_tmpPhoneNumber,_tmpRole,_tmpIsLoggedIn,_tmpLastLoginTime,_tmpCreatedAt);
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
  public Flow<User> getCurrentUserFlow() {
    final String _sql = "SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isLoggedIn");
          final int _cursorIndexOfLastLoginTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLoginTime");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final UserRole _tmpRole;
            _tmpRole = __UserRole_stringToEnum(_cursor.getString(_cursorIndexOfRole));
            final boolean _tmpIsLoggedIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLoggedIn);
            _tmpIsLoggedIn = _tmp != 0;
            final long _tmpLastLoginTime;
            _tmpLastLoginTime = _cursor.getLong(_cursorIndexOfLastLoginTime);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new User(_tmpPhoneNumber,_tmpRole,_tmpIsLoggedIn,_tmpLastLoginTime,_tmpCreatedAt);
          } else {
            _result = null;
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

  private String __UserRole_enumToString(@NonNull final UserRole _value) {
    switch (_value) {
      case STUDENT: return "STUDENT";
      case PARENT: return "PARENT";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private UserRole __UserRole_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "STUDENT": return UserRole.STUDENT;
      case "PARENT": return UserRole.PARENT;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

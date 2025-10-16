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
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.seecolab.istudy.data.local.converter.TypeConverters;
import com.seecolab.istudy.data.model.Difficulty;
import com.seecolab.istudy.data.model.Grade;
import com.seecolab.istudy.data.model.Question;
import com.seecolab.istudy.data.model.QuestionType;
import com.seecolab.istudy.data.model.Subject;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
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
public final class QuestionDao_Impl implements QuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  private final TypeConverters __typeConverters = new TypeConverters();

  private final EntityDeletionOrUpdateAdapter<Question> __deletionAdapterOfQuestion;

  private final EntityDeletionOrUpdateAdapter<Question> __updateAdapterOfQuestion;

  public QuestionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `questions` (`id`,`subject`,`grade`,`chapter`,`questionText`,`questionType`,`options`,`correctAnswer`,`explanation`,`difficulty`,`points`,`tags`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Question entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __Subject_enumToString(entity.getSubject()));
        statement.bindString(3, __Grade_enumToString(entity.getGrade()));
        statement.bindString(4, entity.getChapter());
        statement.bindString(5, entity.getQuestionText());
        statement.bindString(6, __QuestionType_enumToString(entity.getQuestionType()));
        final String _tmp = __typeConverters.fromStringList(entity.getOptions());
        statement.bindString(7, _tmp);
        statement.bindString(8, entity.getCorrectAnswer());
        statement.bindString(9, entity.getExplanation());
        statement.bindString(10, __Difficulty_enumToString(entity.getDifficulty()));
        statement.bindLong(11, entity.getPoints());
        final String _tmp_1 = __typeConverters.fromStringList(entity.getTags());
        statement.bindString(12, _tmp_1);
        statement.bindLong(13, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfQuestion = new EntityDeletionOrUpdateAdapter<Question>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `questions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Question entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfQuestion = new EntityDeletionOrUpdateAdapter<Question>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `questions` SET `id` = ?,`subject` = ?,`grade` = ?,`chapter` = ?,`questionText` = ?,`questionType` = ?,`options` = ?,`correctAnswer` = ?,`explanation` = ?,`difficulty` = ?,`points` = ?,`tags` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Question entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __Subject_enumToString(entity.getSubject()));
        statement.bindString(3, __Grade_enumToString(entity.getGrade()));
        statement.bindString(4, entity.getChapter());
        statement.bindString(5, entity.getQuestionText());
        statement.bindString(6, __QuestionType_enumToString(entity.getQuestionType()));
        final String _tmp = __typeConverters.fromStringList(entity.getOptions());
        statement.bindString(7, _tmp);
        statement.bindString(8, entity.getCorrectAnswer());
        statement.bindString(9, entity.getExplanation());
        statement.bindString(10, __Difficulty_enumToString(entity.getDifficulty()));
        statement.bindLong(11, entity.getPoints());
        final String _tmp_1 = __typeConverters.fromStringList(entity.getTags());
        statement.bindString(12, _tmp_1);
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getId());
      }
    };
  }

  @Override
  public Object insertQuestion(final Question question,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuestion.insertAndReturnId(question);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertQuestions(final List<Question> questions,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuestion.insert(questions);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQuestion(final Question question,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfQuestion.handle(question);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateQuestion(final Question question,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfQuestion.handle(question);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getQuestionById(final long id, final Continuation<? super Question> $completion) {
    final String _sql = "SELECT * FROM questions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Question>() {
      @Override
      @Nullable
      public Question call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
          final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Question _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final String _tmpChapter;
            _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            final String _tmpQuestionText;
            _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
            final QuestionType _tmpQuestionType;
            _tmpQuestionType = __QuestionType_stringToEnum(_cursor.getString(_cursorIndexOfQuestionType));
            final List<String> _tmpOptions;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOptions);
            _tmpOptions = __typeConverters.toStringList(_tmp);
            final String _tmpCorrectAnswer;
            _tmpCorrectAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
            final String _tmpExplanation;
            _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            final Difficulty _tmpDifficulty;
            _tmpDifficulty = __Difficulty_stringToEnum(_cursor.getString(_cursorIndexOfDifficulty));
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final List<String> _tmpTags;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __typeConverters.toStringList(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Question(_tmpId,_tmpSubject,_tmpGrade,_tmpChapter,_tmpQuestionText,_tmpQuestionType,_tmpOptions,_tmpCorrectAnswer,_tmpExplanation,_tmpDifficulty,_tmpPoints,_tmpTags,_tmpCreatedAt);
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
  public Flow<List<Question>> getQuestionsBySubjectAndGrade(final Subject subject,
      final Grade grade) {
    final String _sql = "SELECT * FROM questions WHERE subject = ? AND grade = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __Subject_enumToString(subject));
    _argIndex = 2;
    _statement.bindString(_argIndex, __Grade_enumToString(grade));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"questions"}, new Callable<List<Question>>() {
      @Override
      @NonNull
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
          final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Question _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final String _tmpChapter;
            _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            final String _tmpQuestionText;
            _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
            final QuestionType _tmpQuestionType;
            _tmpQuestionType = __QuestionType_stringToEnum(_cursor.getString(_cursorIndexOfQuestionType));
            final List<String> _tmpOptions;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOptions);
            _tmpOptions = __typeConverters.toStringList(_tmp);
            final String _tmpCorrectAnswer;
            _tmpCorrectAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
            final String _tmpExplanation;
            _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            final Difficulty _tmpDifficulty;
            _tmpDifficulty = __Difficulty_stringToEnum(_cursor.getString(_cursorIndexOfDifficulty));
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final List<String> _tmpTags;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __typeConverters.toStringList(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Question(_tmpId,_tmpSubject,_tmpGrade,_tmpChapter,_tmpQuestionText,_tmpQuestionType,_tmpOptions,_tmpCorrectAnswer,_tmpExplanation,_tmpDifficulty,_tmpPoints,_tmpTags,_tmpCreatedAt);
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
  public Flow<List<Question>> getQuestionsByDifficulty(final Subject subject, final Grade grade,
      final Difficulty difficulty) {
    final String _sql = "SELECT * FROM questions WHERE subject = ? AND grade = ? AND difficulty = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __Subject_enumToString(subject));
    _argIndex = 2;
    _statement.bindString(_argIndex, __Grade_enumToString(grade));
    _argIndex = 3;
    _statement.bindString(_argIndex, __Difficulty_enumToString(difficulty));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"questions"}, new Callable<List<Question>>() {
      @Override
      @NonNull
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
          final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Question _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final String _tmpChapter;
            _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            final String _tmpQuestionText;
            _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
            final QuestionType _tmpQuestionType;
            _tmpQuestionType = __QuestionType_stringToEnum(_cursor.getString(_cursorIndexOfQuestionType));
            final List<String> _tmpOptions;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOptions);
            _tmpOptions = __typeConverters.toStringList(_tmp);
            final String _tmpCorrectAnswer;
            _tmpCorrectAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
            final String _tmpExplanation;
            _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            final Difficulty _tmpDifficulty;
            _tmpDifficulty = __Difficulty_stringToEnum(_cursor.getString(_cursorIndexOfDifficulty));
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final List<String> _tmpTags;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __typeConverters.toStringList(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Question(_tmpId,_tmpSubject,_tmpGrade,_tmpChapter,_tmpQuestionText,_tmpQuestionType,_tmpOptions,_tmpCorrectAnswer,_tmpExplanation,_tmpDifficulty,_tmpPoints,_tmpTags,_tmpCreatedAt);
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
  public Object getQuestionsByIds(final List<Long> questionIds,
      final Continuation<? super List<Question>> $completion) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM questions WHERE id IN (");
    final int _inputSize = questionIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (long _item : questionIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Question>>() {
      @Override
      @NonNull
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
          final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
          final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfPoints = CursorUtil.getColumnIndexOrThrow(_cursor, "points");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Question _item_1;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Subject _tmpSubject;
            _tmpSubject = __Subject_stringToEnum(_cursor.getString(_cursorIndexOfSubject));
            final Grade _tmpGrade;
            _tmpGrade = __Grade_stringToEnum(_cursor.getString(_cursorIndexOfGrade));
            final String _tmpChapter;
            _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            final String _tmpQuestionText;
            _tmpQuestionText = _cursor.getString(_cursorIndexOfQuestionText);
            final QuestionType _tmpQuestionType;
            _tmpQuestionType = __QuestionType_stringToEnum(_cursor.getString(_cursorIndexOfQuestionType));
            final List<String> _tmpOptions;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfOptions);
            _tmpOptions = __typeConverters.toStringList(_tmp);
            final String _tmpCorrectAnswer;
            _tmpCorrectAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
            final String _tmpExplanation;
            _tmpExplanation = _cursor.getString(_cursorIndexOfExplanation);
            final Difficulty _tmpDifficulty;
            _tmpDifficulty = __Difficulty_stringToEnum(_cursor.getString(_cursorIndexOfDifficulty));
            final int _tmpPoints;
            _tmpPoints = _cursor.getInt(_cursorIndexOfPoints);
            final List<String> _tmpTags;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __typeConverters.toStringList(_tmp_1);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item_1 = new Question(_tmpId,_tmpSubject,_tmpGrade,_tmpChapter,_tmpQuestionText,_tmpQuestionType,_tmpOptions,_tmpCorrectAnswer,_tmpExplanation,_tmpDifficulty,_tmpPoints,_tmpTags,_tmpCreatedAt);
            _result.add(_item_1);
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

  private String __QuestionType_enumToString(@NonNull final QuestionType _value) {
    switch (_value) {
      case MULTIPLE_CHOICE: return "MULTIPLE_CHOICE";
      case TRUE_FALSE: return "TRUE_FALSE";
      case SHORT_ANSWER: return "SHORT_ANSWER";
      case ESSAY: return "ESSAY";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __Difficulty_enumToString(@NonNull final Difficulty _value) {
    switch (_value) {
      case BEGINNER: return "BEGINNER";
      case ELEMENTARY: return "ELEMENTARY";
      case INTERMEDIATE: return "INTERMEDIATE";
      case ADVANCED: return "ADVANCED";
      case EXPERT: return "EXPERT";
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

  private QuestionType __QuestionType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "MULTIPLE_CHOICE": return QuestionType.MULTIPLE_CHOICE;
      case "TRUE_FALSE": return QuestionType.TRUE_FALSE;
      case "SHORT_ANSWER": return QuestionType.SHORT_ANSWER;
      case "ESSAY": return QuestionType.ESSAY;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private Difficulty __Difficulty_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "BEGINNER": return Difficulty.BEGINNER;
      case "ELEMENTARY": return Difficulty.ELEMENTARY;
      case "INTERMEDIATE": return Difficulty.INTERMEDIATE;
      case "ADVANCED": return Difficulty.ADVANCED;
      case "EXPERT": return Difficulty.EXPERT;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}

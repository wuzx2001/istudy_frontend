package com.seecolab.istudy.di;

import com.seecolab.istudy.data.local.StudyDatabase;
import com.seecolab.istudy.data.local.dao.StudentDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvideStudentDaoFactory implements Factory<StudentDao> {
  private final Provider<StudyDatabase> databaseProvider;

  public DatabaseModule_ProvideStudentDaoFactory(Provider<StudyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StudentDao get() {
    return provideStudentDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStudentDaoFactory create(
      Provider<StudyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStudentDaoFactory(databaseProvider);
  }

  public static StudentDao provideStudentDao(StudyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStudentDao(database));
  }
}

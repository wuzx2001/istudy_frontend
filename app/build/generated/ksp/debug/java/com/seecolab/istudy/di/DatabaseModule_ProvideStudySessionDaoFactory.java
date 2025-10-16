package com.seecolab.istudy.di;

import com.seecolab.istudy.data.local.StudyDatabase;
import com.seecolab.istudy.data.local.dao.StudySessionDao;
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
public final class DatabaseModule_ProvideStudySessionDaoFactory implements Factory<StudySessionDao> {
  private final Provider<StudyDatabase> databaseProvider;

  public DatabaseModule_ProvideStudySessionDaoFactory(Provider<StudyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StudySessionDao get() {
    return provideStudySessionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStudySessionDaoFactory create(
      Provider<StudyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStudySessionDaoFactory(databaseProvider);
  }

  public static StudySessionDao provideStudySessionDao(StudyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStudySessionDao(database));
  }
}

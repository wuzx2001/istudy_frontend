package com.seecolab.istudy.di;

import com.seecolab.istudy.data.local.StudyDatabase;
import com.seecolab.istudy.data.local.dao.TestSubmissionDao;
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
public final class DatabaseModule_ProvideTestSubmissionDaoFactory implements Factory<TestSubmissionDao> {
  private final Provider<StudyDatabase> databaseProvider;

  public DatabaseModule_ProvideTestSubmissionDaoFactory(Provider<StudyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TestSubmissionDao get() {
    return provideTestSubmissionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTestSubmissionDaoFactory create(
      Provider<StudyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTestSubmissionDaoFactory(databaseProvider);
  }

  public static TestSubmissionDao provideTestSubmissionDao(StudyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTestSubmissionDao(database));
  }
}

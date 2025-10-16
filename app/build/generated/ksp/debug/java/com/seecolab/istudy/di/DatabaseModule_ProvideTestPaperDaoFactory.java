package com.seecolab.istudy.di;

import com.seecolab.istudy.data.local.StudyDatabase;
import com.seecolab.istudy.data.local.dao.TestPaperDao;
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
public final class DatabaseModule_ProvideTestPaperDaoFactory implements Factory<TestPaperDao> {
  private final Provider<StudyDatabase> databaseProvider;

  public DatabaseModule_ProvideTestPaperDaoFactory(Provider<StudyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TestPaperDao get() {
    return provideTestPaperDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTestPaperDaoFactory create(
      Provider<StudyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTestPaperDaoFactory(databaseProvider);
  }

  public static TestPaperDao provideTestPaperDao(StudyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTestPaperDao(database));
  }
}

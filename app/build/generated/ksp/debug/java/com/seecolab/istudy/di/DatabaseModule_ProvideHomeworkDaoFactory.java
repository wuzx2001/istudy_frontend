package com.seecolab.istudy.di;

import com.seecolab.istudy.data.local.StudyDatabase;
import com.seecolab.istudy.data.local.dao.HomeworkDao;
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
public final class DatabaseModule_ProvideHomeworkDaoFactory implements Factory<HomeworkDao> {
  private final Provider<StudyDatabase> databaseProvider;

  public DatabaseModule_ProvideHomeworkDaoFactory(Provider<StudyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public HomeworkDao get() {
    return provideHomeworkDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideHomeworkDaoFactory create(
      Provider<StudyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideHomeworkDaoFactory(databaseProvider);
  }

  public static HomeworkDao provideHomeworkDao(StudyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHomeworkDao(database));
  }
}

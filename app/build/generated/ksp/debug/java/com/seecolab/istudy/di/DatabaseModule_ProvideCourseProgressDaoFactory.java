package com.seecolab.istudy.di;

import com.seecolab.istudy.data.local.StudyDatabase;
import com.seecolab.istudy.data.local.dao.CourseProgressDao;
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
public final class DatabaseModule_ProvideCourseProgressDaoFactory implements Factory<CourseProgressDao> {
  private final Provider<StudyDatabase> databaseProvider;

  public DatabaseModule_ProvideCourseProgressDaoFactory(Provider<StudyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CourseProgressDao get() {
    return provideCourseProgressDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideCourseProgressDaoFactory create(
      Provider<StudyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideCourseProgressDaoFactory(databaseProvider);
  }

  public static CourseProgressDao provideCourseProgressDao(StudyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCourseProgressDao(database));
  }
}

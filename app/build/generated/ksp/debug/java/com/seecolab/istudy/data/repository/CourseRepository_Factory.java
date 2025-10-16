package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.local.dao.CourseDao;
import com.seecolab.istudy.data.local.dao.CourseProgressDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CourseRepository_Factory implements Factory<CourseRepository> {
  private final Provider<CourseDao> courseDaoProvider;

  private final Provider<CourseProgressDao> progressDaoProvider;

  public CourseRepository_Factory(Provider<CourseDao> courseDaoProvider,
      Provider<CourseProgressDao> progressDaoProvider) {
    this.courseDaoProvider = courseDaoProvider;
    this.progressDaoProvider = progressDaoProvider;
  }

  @Override
  public CourseRepository get() {
    return newInstance(courseDaoProvider.get(), progressDaoProvider.get());
  }

  public static CourseRepository_Factory create(Provider<CourseDao> courseDaoProvider,
      Provider<CourseProgressDao> progressDaoProvider) {
    return new CourseRepository_Factory(courseDaoProvider, progressDaoProvider);
  }

  public static CourseRepository newInstance(CourseDao courseDao, CourseProgressDao progressDao) {
    return new CourseRepository(courseDao, progressDao);
  }
}

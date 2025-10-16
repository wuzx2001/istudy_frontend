package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.local.dao.HomeworkDao;
import com.seecolab.istudy.data.local.dao.LearningReportDao;
import com.seecolab.istudy.data.local.dao.StudySessionDao;
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
public final class HomeworkRepository_Factory implements Factory<HomeworkRepository> {
  private final Provider<HomeworkDao> homeworkDaoProvider;

  private final Provider<LearningReportDao> reportDaoProvider;

  private final Provider<StudySessionDao> sessionDaoProvider;

  public HomeworkRepository_Factory(Provider<HomeworkDao> homeworkDaoProvider,
      Provider<LearningReportDao> reportDaoProvider, Provider<StudySessionDao> sessionDaoProvider) {
    this.homeworkDaoProvider = homeworkDaoProvider;
    this.reportDaoProvider = reportDaoProvider;
    this.sessionDaoProvider = sessionDaoProvider;
  }

  @Override
  public HomeworkRepository get() {
    return newInstance(homeworkDaoProvider.get(), reportDaoProvider.get(), sessionDaoProvider.get());
  }

  public static HomeworkRepository_Factory create(Provider<HomeworkDao> homeworkDaoProvider,
      Provider<LearningReportDao> reportDaoProvider, Provider<StudySessionDao> sessionDaoProvider) {
    return new HomeworkRepository_Factory(homeworkDaoProvider, reportDaoProvider, sessionDaoProvider);
  }

  public static HomeworkRepository newInstance(HomeworkDao homeworkDao, LearningReportDao reportDao,
      StudySessionDao sessionDao) {
    return new HomeworkRepository(homeworkDao, reportDao, sessionDao);
  }
}

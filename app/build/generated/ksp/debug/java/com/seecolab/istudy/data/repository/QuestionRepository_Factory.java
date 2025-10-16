package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.local.dao.QuestionDao;
import com.seecolab.istudy.data.local.dao.TestPaperDao;
import com.seecolab.istudy.data.local.dao.TestSubmissionDao;
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
public final class QuestionRepository_Factory implements Factory<QuestionRepository> {
  private final Provider<QuestionDao> questionDaoProvider;

  private final Provider<TestPaperDao> testPaperDaoProvider;

  private final Provider<TestSubmissionDao> submissionDaoProvider;

  public QuestionRepository_Factory(Provider<QuestionDao> questionDaoProvider,
      Provider<TestPaperDao> testPaperDaoProvider,
      Provider<TestSubmissionDao> submissionDaoProvider) {
    this.questionDaoProvider = questionDaoProvider;
    this.testPaperDaoProvider = testPaperDaoProvider;
    this.submissionDaoProvider = submissionDaoProvider;
  }

  @Override
  public QuestionRepository get() {
    return newInstance(questionDaoProvider.get(), testPaperDaoProvider.get(), submissionDaoProvider.get());
  }

  public static QuestionRepository_Factory create(Provider<QuestionDao> questionDaoProvider,
      Provider<TestPaperDao> testPaperDaoProvider,
      Provider<TestSubmissionDao> submissionDaoProvider) {
    return new QuestionRepository_Factory(questionDaoProvider, testPaperDaoProvider, submissionDaoProvider);
  }

  public static QuestionRepository newInstance(QuestionDao questionDao, TestPaperDao testPaperDao,
      TestSubmissionDao submissionDao) {
    return new QuestionRepository(questionDao, testPaperDao, submissionDao);
  }
}

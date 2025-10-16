package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.local.dao.StudentDao;
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
public final class StudentRepository_Factory implements Factory<StudentRepository> {
  private final Provider<StudentDao> studentDaoProvider;

  public StudentRepository_Factory(Provider<StudentDao> studentDaoProvider) {
    this.studentDaoProvider = studentDaoProvider;
  }

  @Override
  public StudentRepository get() {
    return newInstance(studentDaoProvider.get());
  }

  public static StudentRepository_Factory create(Provider<StudentDao> studentDaoProvider) {
    return new StudentRepository_Factory(studentDaoProvider);
  }

  public static StudentRepository newInstance(StudentDao studentDao) {
    return new StudentRepository(studentDao);
  }
}

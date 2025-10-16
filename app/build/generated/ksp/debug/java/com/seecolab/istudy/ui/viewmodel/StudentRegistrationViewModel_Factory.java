package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.repository.StudentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class StudentRegistrationViewModel_Factory implements Factory<StudentRegistrationViewModel> {
  private final Provider<StudentRepository> studentRepositoryProvider;

  public StudentRegistrationViewModel_Factory(
      Provider<StudentRepository> studentRepositoryProvider) {
    this.studentRepositoryProvider = studentRepositoryProvider;
  }

  @Override
  public StudentRegistrationViewModel get() {
    return newInstance(studentRepositoryProvider.get());
  }

  public static StudentRegistrationViewModel_Factory create(
      Provider<StudentRepository> studentRepositoryProvider) {
    return new StudentRegistrationViewModel_Factory(studentRepositoryProvider);
  }

  public static StudentRegistrationViewModel newInstance(StudentRepository studentRepository) {
    return new StudentRegistrationViewModel(studentRepository);
  }
}

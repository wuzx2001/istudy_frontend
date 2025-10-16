package com.seecolab.istudy.ui.screens.teacher;

import com.seecolab.istudy.data.api.TeacherService;
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
public final class TeacherDetailViewModel_Factory implements Factory<TeacherDetailViewModel> {
  private final Provider<TeacherService> teacherServiceProvider;

  public TeacherDetailViewModel_Factory(Provider<TeacherService> teacherServiceProvider) {
    this.teacherServiceProvider = teacherServiceProvider;
  }

  @Override
  public TeacherDetailViewModel get() {
    return newInstance(teacherServiceProvider.get());
  }

  public static TeacherDetailViewModel_Factory create(
      Provider<TeacherService> teacherServiceProvider) {
    return new TeacherDetailViewModel_Factory(teacherServiceProvider);
  }

  public static TeacherDetailViewModel newInstance(TeacherService teacherService) {
    return new TeacherDetailViewModel(teacherService);
  }
}

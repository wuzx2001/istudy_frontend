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
public final class TempProviderVM_Factory implements Factory<TempProviderVM> {
  private final Provider<TeacherService> teacherServiceProvider;

  public TempProviderVM_Factory(Provider<TeacherService> teacherServiceProvider) {
    this.teacherServiceProvider = teacherServiceProvider;
  }

  @Override
  public TempProviderVM get() {
    return newInstance(teacherServiceProvider.get());
  }

  public static TempProviderVM_Factory create(Provider<TeacherService> teacherServiceProvider) {
    return new TempProviderVM_Factory(teacherServiceProvider);
  }

  public static TempProviderVM newInstance(TeacherService teacherService) {
    return new TempProviderVM(teacherService);
  }
}

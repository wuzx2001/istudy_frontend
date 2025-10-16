package com.seecolab.istudy.ui.screens.teacher;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.internal.lifecycle.HiltViewModelMap.KeySet")
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
public final class TeacherDetailViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static TeacherDetailViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(TeacherDetailViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final TeacherDetailViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new TeacherDetailViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}

package com.seecolab.istudy.ui.screens.wrongbook;

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
public final class WrongDetailViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static WrongDetailViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(WrongDetailViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final WrongDetailViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new WrongDetailViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}

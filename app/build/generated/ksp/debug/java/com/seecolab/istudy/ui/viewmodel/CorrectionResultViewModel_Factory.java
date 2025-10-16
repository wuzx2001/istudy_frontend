package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.utils.AuthenticationGuard;
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
public final class CorrectionResultViewModel_Factory implements Factory<CorrectionResultViewModel> {
  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public CorrectionResultViewModel_Factory(
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public CorrectionResultViewModel get() {
    return newInstance(authenticationGuardProvider.get());
  }

  public static CorrectionResultViewModel_Factory create(
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new CorrectionResultViewModel_Factory(authenticationGuardProvider);
  }

  public static CorrectionResultViewModel newInstance(AuthenticationGuard authenticationGuard) {
    return new CorrectionResultViewModel(authenticationGuard);
  }
}

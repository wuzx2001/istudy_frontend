package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.repository.AuthRepository;
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
public final class AuthNavigationViewModel_Factory implements Factory<AuthNavigationViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public AuthNavigationViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public AuthNavigationViewModel get() {
    return newInstance(authRepositoryProvider.get(), authenticationGuardProvider.get());
  }

  public static AuthNavigationViewModel_Factory create(
      Provider<AuthRepository> authRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new AuthNavigationViewModel_Factory(authRepositoryProvider, authenticationGuardProvider);
  }

  public static AuthNavigationViewModel newInstance(AuthRepository authRepository,
      AuthenticationGuard authenticationGuard) {
    return new AuthNavigationViewModel(authRepository, authenticationGuard);
  }
}

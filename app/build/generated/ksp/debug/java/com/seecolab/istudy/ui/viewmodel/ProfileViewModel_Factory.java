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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public ProfileViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(authRepositoryProvider.get(), authenticationGuardProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new ProfileViewModel_Factory(authRepositoryProvider, authenticationGuardProvider);
  }

  public static ProfileViewModel newInstance(AuthRepository authRepository,
      AuthenticationGuard authenticationGuard) {
    return new ProfileViewModel(authRepository, authenticationGuard);
  }
}

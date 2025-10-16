package com.seecolab.istudy.utils;

import com.seecolab.istudy.data.repository.AuthRepository;
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
public final class AuthenticationGuard_Factory implements Factory<AuthenticationGuard> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public AuthenticationGuard_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public AuthenticationGuard get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static AuthenticationGuard_Factory create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new AuthenticationGuard_Factory(authRepositoryProvider);
  }

  public static AuthenticationGuard newInstance(AuthRepository authRepository) {
    return new AuthenticationGuard(authRepository);
  }
}

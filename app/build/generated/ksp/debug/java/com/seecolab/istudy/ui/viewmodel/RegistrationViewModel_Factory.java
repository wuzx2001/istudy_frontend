package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.repository.AuthRepository;
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
public final class RegistrationViewModel_Factory implements Factory<RegistrationViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public RegistrationViewModel_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public RegistrationViewModel get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static RegistrationViewModel_Factory create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new RegistrationViewModel_Factory(authRepositoryProvider);
  }

  public static RegistrationViewModel newInstance(AuthRepository authRepository) {
    return new RegistrationViewModel(authRepository);
  }
}

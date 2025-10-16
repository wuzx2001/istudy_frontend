package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.repository.UserRepository;
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
public final class AddStudentViewModel_Factory implements Factory<AddStudentViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public AddStudentViewModel_Factory(Provider<UserRepository> userRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public AddStudentViewModel get() {
    return newInstance(userRepositoryProvider.get(), authenticationGuardProvider.get());
  }

  public static AddStudentViewModel_Factory create(Provider<UserRepository> userRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new AddStudentViewModel_Factory(userRepositoryProvider, authenticationGuardProvider);
  }

  public static AddStudentViewModel newInstance(UserRepository userRepository,
      AuthenticationGuard authenticationGuard) {
    return new AddStudentViewModel(userRepository, authenticationGuard);
  }
}

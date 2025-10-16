package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.repository.HomeworkCorrectionRepository;
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
public final class HomeworkCorrectionViewModel_Factory implements Factory<HomeworkCorrectionViewModel> {
  private final Provider<HomeworkCorrectionRepository> repositoryProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public HomeworkCorrectionViewModel_Factory(
      Provider<HomeworkCorrectionRepository> repositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public HomeworkCorrectionViewModel get() {
    return newInstance(repositoryProvider.get(), authenticationGuardProvider.get());
  }

  public static HomeworkCorrectionViewModel_Factory create(
      Provider<HomeworkCorrectionRepository> repositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new HomeworkCorrectionViewModel_Factory(repositoryProvider, authenticationGuardProvider);
  }

  public static HomeworkCorrectionViewModel newInstance(HomeworkCorrectionRepository repository,
      AuthenticationGuard authenticationGuard) {
    return new HomeworkCorrectionViewModel(repository, authenticationGuard);
  }
}

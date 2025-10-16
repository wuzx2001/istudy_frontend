package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.repository.UploadRepository;
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
public final class UploadResultViewModel_Factory implements Factory<UploadResultViewModel> {
  private final Provider<UploadRepository> uploadRepositoryProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public UploadResultViewModel_Factory(Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.uploadRepositoryProvider = uploadRepositoryProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public UploadResultViewModel get() {
    return newInstance(uploadRepositoryProvider.get(), authenticationGuardProvider.get());
  }

  public static UploadResultViewModel_Factory create(
      Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new UploadResultViewModel_Factory(uploadRepositoryProvider, authenticationGuardProvider);
  }

  public static UploadResultViewModel newInstance(UploadRepository uploadRepository,
      AuthenticationGuard authenticationGuard) {
    return new UploadResultViewModel(uploadRepository, authenticationGuard);
  }
}

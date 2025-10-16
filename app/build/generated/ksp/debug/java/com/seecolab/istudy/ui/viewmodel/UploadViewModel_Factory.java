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
public final class UploadViewModel_Factory implements Factory<UploadViewModel> {
  private final Provider<UploadRepository> uploadRepositoryProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public UploadViewModel_Factory(Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.uploadRepositoryProvider = uploadRepositoryProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public UploadViewModel get() {
    return newInstance(uploadRepositoryProvider.get(), authenticationGuardProvider.get());
  }

  public static UploadViewModel_Factory create(Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new UploadViewModel_Factory(uploadRepositoryProvider, authenticationGuardProvider);
  }

  public static UploadViewModel newInstance(UploadRepository uploadRepository,
      AuthenticationGuard authenticationGuard) {
    return new UploadViewModel(uploadRepository, authenticationGuard);
  }
}

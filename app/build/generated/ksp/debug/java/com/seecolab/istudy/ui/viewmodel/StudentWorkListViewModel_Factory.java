package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.api.UploadService;
import com.seecolab.istudy.data.local.TokenManager;
import com.seecolab.istudy.data.repository.AuthRepository;
import com.seecolab.istudy.data.repository.UploadRepository;
import com.seecolab.istudy.utils.ApiErrorHandler;
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
public final class StudentWorkListViewModel_Factory implements Factory<StudentWorkListViewModel> {
  private final Provider<UploadService> uploadServiceProvider;

  private final Provider<UploadRepository> uploadRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  private final Provider<ApiErrorHandler> apiErrorHandlerProvider;

  public StudentWorkListViewModel_Factory(Provider<UploadService> uploadServiceProvider,
      Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    this.uploadServiceProvider = uploadServiceProvider;
    this.uploadRepositoryProvider = uploadRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
    this.apiErrorHandlerProvider = apiErrorHandlerProvider;
  }

  @Override
  public StudentWorkListViewModel get() {
    return newInstance(uploadServiceProvider.get(), uploadRepositoryProvider.get(), authRepositoryProvider.get(), tokenManagerProvider.get(), authenticationGuardProvider.get(), apiErrorHandlerProvider.get());
  }

  public static StudentWorkListViewModel_Factory create(
      Provider<UploadService> uploadServiceProvider,
      Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    return new StudentWorkListViewModel_Factory(uploadServiceProvider, uploadRepositoryProvider, authRepositoryProvider, tokenManagerProvider, authenticationGuardProvider, apiErrorHandlerProvider);
  }

  public static StudentWorkListViewModel newInstance(UploadService uploadService,
      UploadRepository uploadRepository, AuthRepository authRepository, TokenManager tokenManager,
      AuthenticationGuard authenticationGuard, ApiErrorHandler apiErrorHandler) {
    return new StudentWorkListViewModel(uploadService, uploadRepository, authRepository, tokenManager, authenticationGuard, apiErrorHandler);
  }
}

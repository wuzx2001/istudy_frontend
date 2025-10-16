package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.api.UploadService;
import com.seecolab.istudy.data.local.TokenManager;
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
public final class StudentWorkDetailViewModel_Factory implements Factory<StudentWorkDetailViewModel> {
  private final Provider<UploadService> uploadServiceProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  private final Provider<ApiErrorHandler> apiErrorHandlerProvider;

  public StudentWorkDetailViewModel_Factory(Provider<UploadService> uploadServiceProvider,
      Provider<TokenManager> tokenManagerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    this.uploadServiceProvider = uploadServiceProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
    this.apiErrorHandlerProvider = apiErrorHandlerProvider;
  }

  @Override
  public StudentWorkDetailViewModel get() {
    return newInstance(uploadServiceProvider.get(), tokenManagerProvider.get(), authenticationGuardProvider.get(), apiErrorHandlerProvider.get());
  }

  public static StudentWorkDetailViewModel_Factory create(
      Provider<UploadService> uploadServiceProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    return new StudentWorkDetailViewModel_Factory(uploadServiceProvider, tokenManagerProvider, authenticationGuardProvider, apiErrorHandlerProvider);
  }

  public static StudentWorkDetailViewModel newInstance(UploadService uploadService,
      TokenManager tokenManager, AuthenticationGuard authenticationGuard,
      ApiErrorHandler apiErrorHandler) {
    return new StudentWorkDetailViewModel(uploadService, tokenManager, authenticationGuard, apiErrorHandler);
  }
}

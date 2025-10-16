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
public final class UploadWorkListViewModel_Factory implements Factory<UploadWorkListViewModel> {
  private final Provider<UploadService> uploadServiceProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<ApiErrorHandler> apiErrorHandlerProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public UploadWorkListViewModel_Factory(Provider<UploadService> uploadServiceProvider,
      Provider<TokenManager> tokenManagerProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.uploadServiceProvider = uploadServiceProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.apiErrorHandlerProvider = apiErrorHandlerProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public UploadWorkListViewModel get() {
    return newInstance(uploadServiceProvider.get(), tokenManagerProvider.get(), apiErrorHandlerProvider.get(), authenticationGuardProvider.get());
  }

  public static UploadWorkListViewModel_Factory create(
      Provider<UploadService> uploadServiceProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new UploadWorkListViewModel_Factory(uploadServiceProvider, tokenManagerProvider, apiErrorHandlerProvider, authenticationGuardProvider);
  }

  public static UploadWorkListViewModel newInstance(UploadService uploadService,
      TokenManager tokenManager, ApiErrorHandler apiErrorHandler,
      AuthenticationGuard authenticationGuard) {
    return new UploadWorkListViewModel(uploadService, tokenManager, apiErrorHandler, authenticationGuard);
  }
}

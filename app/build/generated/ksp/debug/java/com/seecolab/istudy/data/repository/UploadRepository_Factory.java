package com.seecolab.istudy.data.repository;

import android.content.Context;
import com.seecolab.istudy.data.api.UploadService;
import com.seecolab.istudy.data.local.TokenManager;
import com.seecolab.istudy.utils.ApiErrorHandler;
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
public final class UploadRepository_Factory implements Factory<UploadRepository> {
  private final Provider<UploadService> uploadServiceProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<Context> contextProvider;

  private final Provider<ApiErrorHandler> apiErrorHandlerProvider;

  public UploadRepository_Factory(Provider<UploadService> uploadServiceProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<Context> contextProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    this.uploadServiceProvider = uploadServiceProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.contextProvider = contextProvider;
    this.apiErrorHandlerProvider = apiErrorHandlerProvider;
  }

  @Override
  public UploadRepository get() {
    return newInstance(uploadServiceProvider.get(), tokenManagerProvider.get(), contextProvider.get(), apiErrorHandlerProvider.get());
  }

  public static UploadRepository_Factory create(Provider<UploadService> uploadServiceProvider,
      Provider<TokenManager> tokenManagerProvider, Provider<Context> contextProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    return new UploadRepository_Factory(uploadServiceProvider, tokenManagerProvider, contextProvider, apiErrorHandlerProvider);
  }

  public static UploadRepository newInstance(UploadService uploadService, TokenManager tokenManager,
      Context context, ApiErrorHandler apiErrorHandler) {
    return new UploadRepository(uploadService, tokenManager, context, apiErrorHandler);
  }
}

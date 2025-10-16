package com.seecolab.istudy.data.repository;

import android.content.Context;
import com.seecolab.istudy.data.api.HomeworkCorrectionApiService;
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
public final class HomeworkCorrectionRepository_Factory implements Factory<HomeworkCorrectionRepository> {
  private final Provider<HomeworkCorrectionApiService> apiServiceProvider;

  private final Provider<Context> contextProvider;

  private final Provider<ApiErrorHandler> apiErrorHandlerProvider;

  public HomeworkCorrectionRepository_Factory(
      Provider<HomeworkCorrectionApiService> apiServiceProvider, Provider<Context> contextProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.contextProvider = contextProvider;
    this.apiErrorHandlerProvider = apiErrorHandlerProvider;
  }

  @Override
  public HomeworkCorrectionRepository get() {
    return newInstance(apiServiceProvider.get(), contextProvider.get(), apiErrorHandlerProvider.get());
  }

  public static HomeworkCorrectionRepository_Factory create(
      Provider<HomeworkCorrectionApiService> apiServiceProvider, Provider<Context> contextProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    return new HomeworkCorrectionRepository_Factory(apiServiceProvider, contextProvider, apiErrorHandlerProvider);
  }

  public static HomeworkCorrectionRepository newInstance(HomeworkCorrectionApiService apiService,
      Context context, ApiErrorHandler apiErrorHandler) {
    return new HomeworkCorrectionRepository(apiService, context, apiErrorHandler);
  }
}

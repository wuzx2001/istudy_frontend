package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.api.UserManagementService;
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
public final class UserRepository_Factory implements Factory<UserRepository> {
  private final Provider<UserManagementService> userManagementServiceProvider;

  private final Provider<ApiErrorHandler> apiErrorHandlerProvider;

  public UserRepository_Factory(Provider<UserManagementService> userManagementServiceProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    this.userManagementServiceProvider = userManagementServiceProvider;
    this.apiErrorHandlerProvider = apiErrorHandlerProvider;
  }

  @Override
  public UserRepository get() {
    return newInstance(userManagementServiceProvider.get(), apiErrorHandlerProvider.get());
  }

  public static UserRepository_Factory create(
      Provider<UserManagementService> userManagementServiceProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider) {
    return new UserRepository_Factory(userManagementServiceProvider, apiErrorHandlerProvider);
  }

  public static UserRepository newInstance(UserManagementService userManagementService,
      ApiErrorHandler apiErrorHandler) {
    return new UserRepository(userManagementService, apiErrorHandler);
  }
}

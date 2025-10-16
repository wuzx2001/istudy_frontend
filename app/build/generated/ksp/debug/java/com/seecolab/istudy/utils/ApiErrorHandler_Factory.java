package com.seecolab.istudy.utils;

import com.seecolab.istudy.data.local.TokenManager;
import com.seecolab.istudy.data.local.dao.UserDao;
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
public final class ApiErrorHandler_Factory implements Factory<ApiErrorHandler> {
  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public ApiErrorHandler_Factory(Provider<TokenManager> tokenManagerProvider,
      Provider<UserDao> userDaoProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.tokenManagerProvider = tokenManagerProvider;
    this.userDaoProvider = userDaoProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public ApiErrorHandler get() {
    return newInstance(tokenManagerProvider.get(), userDaoProvider.get(), authenticationGuardProvider.get());
  }

  public static ApiErrorHandler_Factory create(Provider<TokenManager> tokenManagerProvider,
      Provider<UserDao> userDaoProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new ApiErrorHandler_Factory(tokenManagerProvider, userDaoProvider, authenticationGuardProvider);
  }

  public static ApiErrorHandler newInstance(TokenManager tokenManager, UserDao userDao,
      AuthenticationGuard authenticationGuard) {
    return new ApiErrorHandler(tokenManager, userDao, authenticationGuard);
  }
}

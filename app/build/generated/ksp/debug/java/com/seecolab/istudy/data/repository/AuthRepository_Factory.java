package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.api.AuthService;
import com.seecolab.istudy.data.api.LegacyAuthService;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<AuthService> authServiceProvider;

  private final Provider<LegacyAuthService> legacyAuthServiceProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public AuthRepository_Factory(Provider<AuthService> authServiceProvider,
      Provider<LegacyAuthService> legacyAuthServiceProvider, Provider<UserDao> userDaoProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.authServiceProvider = authServiceProvider;
    this.legacyAuthServiceProvider = legacyAuthServiceProvider;
    this.userDaoProvider = userDaoProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(authServiceProvider.get(), legacyAuthServiceProvider.get(), userDaoProvider.get(), tokenManagerProvider.get());
  }

  public static AuthRepository_Factory create(Provider<AuthService> authServiceProvider,
      Provider<LegacyAuthService> legacyAuthServiceProvider, Provider<UserDao> userDaoProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new AuthRepository_Factory(authServiceProvider, legacyAuthServiceProvider, userDaoProvider, tokenManagerProvider);
  }

  public static AuthRepository newInstance(AuthService authService,
      LegacyAuthService legacyAuthService, UserDao userDao, TokenManager tokenManager) {
    return new AuthRepository(authService, legacyAuthService, userDao, tokenManager);
  }
}

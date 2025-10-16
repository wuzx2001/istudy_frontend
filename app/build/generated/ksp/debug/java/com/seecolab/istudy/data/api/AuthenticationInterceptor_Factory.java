package com.seecolab.istudy.data.api;

import com.seecolab.istudy.data.local.TokenManager;
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
public final class AuthenticationInterceptor_Factory implements Factory<AuthenticationInterceptor> {
  private final Provider<TokenManager> tokenManagerProvider;

  public AuthenticationInterceptor_Factory(Provider<TokenManager> tokenManagerProvider) {
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public AuthenticationInterceptor get() {
    return newInstance(tokenManagerProvider.get());
  }

  public static AuthenticationInterceptor_Factory create(
      Provider<TokenManager> tokenManagerProvider) {
    return new AuthenticationInterceptor_Factory(tokenManagerProvider);
  }

  public static AuthenticationInterceptor newInstance(TokenManager tokenManager) {
    return new AuthenticationInterceptor(tokenManager);
  }
}

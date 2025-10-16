package com.seecolab.istudy.di;

import com.seecolab.istudy.data.api.AuthenticationInterceptor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<HttpLoggingInterceptor> loggingInterceptorProvider;

  private final Provider<AuthenticationInterceptor> authInterceptorProvider;

  public NetworkModule_ProvideOkHttpClientFactory(
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider,
      Provider<AuthenticationInterceptor> authInterceptorProvider) {
    this.loggingInterceptorProvider = loggingInterceptorProvider;
    this.authInterceptorProvider = authInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(loggingInterceptorProvider.get(), authInterceptorProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(
      Provider<HttpLoggingInterceptor> loggingInterceptorProvider,
      Provider<AuthenticationInterceptor> authInterceptorProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(loggingInterceptorProvider, authInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor,
      AuthenticationInterceptor authInterceptor) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(loggingInterceptor, authInterceptor));
  }
}

package com.seecolab.istudy.di;

import com.seecolab.istudy.data.api.LegacyAuthService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideLegacyAuthServiceFactory implements Factory<LegacyAuthService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideLegacyAuthServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public LegacyAuthService get() {
    return provideLegacyAuthService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideLegacyAuthServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideLegacyAuthServiceFactory(retrofitProvider);
  }

  public static LegacyAuthService provideLegacyAuthService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideLegacyAuthService(retrofit));
  }
}

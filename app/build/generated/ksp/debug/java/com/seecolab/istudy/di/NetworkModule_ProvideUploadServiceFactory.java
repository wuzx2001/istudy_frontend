package com.seecolab.istudy.di;

import com.seecolab.istudy.data.api.UploadService;
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
public final class NetworkModule_ProvideUploadServiceFactory implements Factory<UploadService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideUploadServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public UploadService get() {
    return provideUploadService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideUploadServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideUploadServiceFactory(retrofitProvider);
  }

  public static UploadService provideUploadService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideUploadService(retrofit));
  }
}

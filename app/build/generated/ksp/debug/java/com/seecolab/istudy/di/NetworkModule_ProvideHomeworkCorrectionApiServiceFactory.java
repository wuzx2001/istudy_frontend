package com.seecolab.istudy.di;

import com.seecolab.istudy.data.api.HomeworkCorrectionApiService;
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
public final class NetworkModule_ProvideHomeworkCorrectionApiServiceFactory implements Factory<HomeworkCorrectionApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideHomeworkCorrectionApiServiceFactory(
      Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public HomeworkCorrectionApiService get() {
    return provideHomeworkCorrectionApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideHomeworkCorrectionApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideHomeworkCorrectionApiServiceFactory(retrofitProvider);
  }

  public static HomeworkCorrectionApiService provideHomeworkCorrectionApiService(
      Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideHomeworkCorrectionApiService(retrofit));
  }
}

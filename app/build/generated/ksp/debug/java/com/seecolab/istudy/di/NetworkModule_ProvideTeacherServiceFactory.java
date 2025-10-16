package com.seecolab.istudy.di;

import com.seecolab.istudy.data.api.TeacherService;
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
public final class NetworkModule_ProvideTeacherServiceFactory implements Factory<TeacherService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideTeacherServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public TeacherService get() {
    return provideTeacherService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideTeacherServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideTeacherServiceFactory(retrofitProvider);
  }

  public static TeacherService provideTeacherService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideTeacherService(retrofit));
  }
}

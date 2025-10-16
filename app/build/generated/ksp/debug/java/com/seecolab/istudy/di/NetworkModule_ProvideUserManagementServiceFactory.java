package com.seecolab.istudy.di;

import com.seecolab.istudy.data.api.UserManagementService;
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
public final class NetworkModule_ProvideUserManagementServiceFactory implements Factory<UserManagementService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideUserManagementServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public UserManagementService get() {
    return provideUserManagementService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideUserManagementServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideUserManagementServiceFactory(retrofitProvider);
  }

  public static UserManagementService provideUserManagementService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideUserManagementService(retrofit));
  }
}

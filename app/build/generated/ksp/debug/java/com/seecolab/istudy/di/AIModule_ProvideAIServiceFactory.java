package com.seecolab.istudy.di;

import com.seecolab.istudy.data.api.AIService;
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
public final class AIModule_ProvideAIServiceFactory implements Factory<AIService> {
  private final Provider<Retrofit> retrofitProvider;

  public AIModule_ProvideAIServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public AIService get() {
    return provideAIService(retrofitProvider.get());
  }

  public static AIModule_ProvideAIServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new AIModule_ProvideAIServiceFactory(retrofitProvider);
  }

  public static AIService provideAIService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AIModule.INSTANCE.provideAIService(retrofit));
  }
}

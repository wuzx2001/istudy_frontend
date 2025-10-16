package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.api.AIService;
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
public final class AIRepository_Factory implements Factory<AIRepository> {
  private final Provider<AIService> aiServiceProvider;

  public AIRepository_Factory(Provider<AIService> aiServiceProvider) {
    this.aiServiceProvider = aiServiceProvider;
  }

  @Override
  public AIRepository get() {
    return newInstance(aiServiceProvider.get());
  }

  public static AIRepository_Factory create(Provider<AIService> aiServiceProvider) {
    return new AIRepository_Factory(aiServiceProvider);
  }

  public static AIRepository newInstance(AIService aiService) {
    return new AIRepository(aiService);
  }
}

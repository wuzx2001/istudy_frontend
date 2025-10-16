package com.seecolab.istudy.di;

import android.content.Context;
import com.seecolab.istudy.data.local.StudyDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideStudyDatabaseFactory implements Factory<StudyDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideStudyDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public StudyDatabase get() {
    return provideStudyDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideStudyDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideStudyDatabaseFactory(contextProvider);
  }

  public static StudyDatabase provideStudyDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStudyDatabase(context));
  }
}

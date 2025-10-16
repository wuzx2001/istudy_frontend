package com.seecolab.istudy.ui.screens.wrongbook;

import com.seecolab.istudy.data.repository.UploadRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class WrongDetailViewModel_Factory implements Factory<WrongDetailViewModel> {
  private final Provider<UploadRepository> uploadRepositoryProvider;

  public WrongDetailViewModel_Factory(Provider<UploadRepository> uploadRepositoryProvider) {
    this.uploadRepositoryProvider = uploadRepositoryProvider;
  }

  @Override
  public WrongDetailViewModel get() {
    return newInstance(uploadRepositoryProvider.get());
  }

  public static WrongDetailViewModel_Factory create(
      Provider<UploadRepository> uploadRepositoryProvider) {
    return new WrongDetailViewModel_Factory(uploadRepositoryProvider);
  }

  public static WrongDetailViewModel newInstance(UploadRepository uploadRepository) {
    return new WrongDetailViewModel(uploadRepository);
  }
}

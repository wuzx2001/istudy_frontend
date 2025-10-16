package com.seecolab.istudy.ui.screens.wrongbook;

import com.seecolab.istudy.data.local.TokenManager;
import com.seecolab.istudy.data.repository.AuthRepository;
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
public final class WrongBookViewModel_Factory implements Factory<WrongBookViewModel> {
  private final Provider<UploadRepository> uploadRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public WrongBookViewModel_Factory(Provider<UploadRepository> uploadRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.uploadRepositoryProvider = uploadRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public WrongBookViewModel get() {
    return newInstance(uploadRepositoryProvider.get(), tokenManagerProvider.get(), authRepositoryProvider.get());
  }

  public static WrongBookViewModel_Factory create(
      Provider<UploadRepository> uploadRepositoryProvider,
      Provider<TokenManager> tokenManagerProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new WrongBookViewModel_Factory(uploadRepositoryProvider, tokenManagerProvider, authRepositoryProvider);
  }

  public static WrongBookViewModel newInstance(UploadRepository uploadRepository,
      TokenManager tokenManager, AuthRepository authRepository) {
    return new WrongBookViewModel(uploadRepository, tokenManager, authRepository);
  }
}

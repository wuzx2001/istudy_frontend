package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.api.UploadService;
import com.seecolab.istudy.data.local.TokenManager;
import com.seecolab.istudy.data.repository.AuthRepository;
import com.seecolab.istudy.data.repository.HomeworkRepository;
import com.seecolab.istudy.data.repository.StudentRepository;
import com.seecolab.istudy.data.repository.UploadRepository;
import com.seecolab.istudy.data.repository.UserRepository;
import com.seecolab.istudy.utils.ApiErrorHandler;
import com.seecolab.istudy.utils.AuthenticationGuard;
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
public final class ParentZoneViewModel_Factory implements Factory<ParentZoneViewModel> {
  private final Provider<HomeworkRepository> homeworkRepositoryProvider;

  private final Provider<StudentRepository> studentRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<UploadService> uploadServiceProvider;

  private final Provider<UploadRepository> uploadRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  private final Provider<ApiErrorHandler> apiErrorHandlerProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public ParentZoneViewModel_Factory(Provider<HomeworkRepository> homeworkRepositoryProvider,
      Provider<StudentRepository> studentRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<UploadService> uploadServiceProvider,
      Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.homeworkRepositoryProvider = homeworkRepositoryProvider;
    this.studentRepositoryProvider = studentRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.uploadServiceProvider = uploadServiceProvider;
    this.uploadRepositoryProvider = uploadRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.tokenManagerProvider = tokenManagerProvider;
    this.apiErrorHandlerProvider = apiErrorHandlerProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public ParentZoneViewModel get() {
    return newInstance(homeworkRepositoryProvider.get(), studentRepositoryProvider.get(), userRepositoryProvider.get(), uploadServiceProvider.get(), uploadRepositoryProvider.get(), authRepositoryProvider.get(), tokenManagerProvider.get(), apiErrorHandlerProvider.get(), authenticationGuardProvider.get());
  }

  public static ParentZoneViewModel_Factory create(
      Provider<HomeworkRepository> homeworkRepositoryProvider,
      Provider<StudentRepository> studentRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<UploadService> uploadServiceProvider,
      Provider<UploadRepository> uploadRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider, Provider<TokenManager> tokenManagerProvider,
      Provider<ApiErrorHandler> apiErrorHandlerProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new ParentZoneViewModel_Factory(homeworkRepositoryProvider, studentRepositoryProvider, userRepositoryProvider, uploadServiceProvider, uploadRepositoryProvider, authRepositoryProvider, tokenManagerProvider, apiErrorHandlerProvider, authenticationGuardProvider);
  }

  public static ParentZoneViewModel newInstance(HomeworkRepository homeworkRepository,
      StudentRepository studentRepository, UserRepository userRepository,
      UploadService uploadService, UploadRepository uploadRepository, AuthRepository authRepository,
      TokenManager tokenManager, ApiErrorHandler apiErrorHandler,
      AuthenticationGuard authenticationGuard) {
    return new ParentZoneViewModel(homeworkRepository, studentRepository, userRepository, uploadService, uploadRepository, authRepository, tokenManager, apiErrorHandler, authenticationGuard);
  }
}

package com.seecolab.istudy.ui.viewmodel;

import com.seecolab.istudy.data.repository.TeacherRepository;
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
public final class TeacherRecommendationViewModel_Factory implements Factory<TeacherRecommendationViewModel> {
  private final Provider<TeacherRepository> teacherRepositoryProvider;

  private final Provider<AuthenticationGuard> authenticationGuardProvider;

  public TeacherRecommendationViewModel_Factory(
      Provider<TeacherRepository> teacherRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    this.teacherRepositoryProvider = teacherRepositoryProvider;
    this.authenticationGuardProvider = authenticationGuardProvider;
  }

  @Override
  public TeacherRecommendationViewModel get() {
    return newInstance(teacherRepositoryProvider.get(), authenticationGuardProvider.get());
  }

  public static TeacherRecommendationViewModel_Factory create(
      Provider<TeacherRepository> teacherRepositoryProvider,
      Provider<AuthenticationGuard> authenticationGuardProvider) {
    return new TeacherRecommendationViewModel_Factory(teacherRepositoryProvider, authenticationGuardProvider);
  }

  public static TeacherRecommendationViewModel newInstance(TeacherRepository teacherRepository,
      AuthenticationGuard authenticationGuard) {
    return new TeacherRecommendationViewModel(teacherRepository, authenticationGuard);
  }
}

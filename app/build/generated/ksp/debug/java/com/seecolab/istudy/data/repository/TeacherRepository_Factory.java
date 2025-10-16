package com.seecolab.istudy.data.repository;

import com.seecolab.istudy.data.api.TeacherService;
import com.seecolab.istudy.data.local.dao.TeacherBookingDao;
import com.seecolab.istudy.data.local.dao.TeacherDao;
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
public final class TeacherRepository_Factory implements Factory<TeacherRepository> {
  private final Provider<TeacherDao> teacherDaoProvider;

  private final Provider<TeacherBookingDao> bookingDaoProvider;

  private final Provider<TeacherService> teacherServiceProvider;

  public TeacherRepository_Factory(Provider<TeacherDao> teacherDaoProvider,
      Provider<TeacherBookingDao> bookingDaoProvider,
      Provider<TeacherService> teacherServiceProvider) {
    this.teacherDaoProvider = teacherDaoProvider;
    this.bookingDaoProvider = bookingDaoProvider;
    this.teacherServiceProvider = teacherServiceProvider;
  }

  @Override
  public TeacherRepository get() {
    return newInstance(teacherDaoProvider.get(), bookingDaoProvider.get(), teacherServiceProvider.get());
  }

  public static TeacherRepository_Factory create(Provider<TeacherDao> teacherDaoProvider,
      Provider<TeacherBookingDao> bookingDaoProvider,
      Provider<TeacherService> teacherServiceProvider) {
    return new TeacherRepository_Factory(teacherDaoProvider, bookingDaoProvider, teacherServiceProvider);
  }

  public static TeacherRepository newInstance(TeacherDao teacherDao, TeacherBookingDao bookingDao,
      TeacherService teacherService) {
    return new TeacherRepository(teacherDao, bookingDao, teacherService);
  }
}

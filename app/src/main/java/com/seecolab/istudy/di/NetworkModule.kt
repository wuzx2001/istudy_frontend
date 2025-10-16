package com.seecolab.istudy.di

import android.content.Context
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.seecolab.istudy.data.api.*
import com.seecolab.istudy.data.model.SexEnum
import com.seecolab.istudy.data.model.GradeEnum
import com.seecolab.istudy.data.model.UserTypeEnum
import com.seecolab.istudy.utils.AuthenticationGuard
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.seecolab.istudy.data.api.TeacherService
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)  // Add authentication first
            .addInterceptor(loggingInterceptor)
            .connectTimeout(ApiConfiguration.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ApiConfiguration.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ApiConfiguration.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .registerTypeAdapter(SexEnum::class.java, object : JsonSerializer<SexEnum> {
                override fun serialize(src: SexEnum, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
                    return JsonPrimitive(src.value)
                }
            })
            .registerTypeAdapter(SexEnum::class.java, object : JsonDeserializer<SexEnum> {
                override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): SexEnum {
                    val value = json.asString
                    return SexEnum.values().find { it.value == value } ?: SexEnum.OTHER
                }
            })
            .registerTypeAdapter(GradeEnum::class.java, object : JsonSerializer<GradeEnum> {
                override fun serialize(src: GradeEnum, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
                    return JsonPrimitive(src.value)
                }
            })
            .registerTypeAdapter(GradeEnum::class.java, object : JsonDeserializer<GradeEnum> {
                override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): GradeEnum {
                    val value = json.asString
                    return GradeEnum.values().find { it.value == value } ?: GradeEnum.GRADE_1
                }
            })
            .registerTypeAdapter(UserTypeEnum::class.java, object : JsonSerializer<UserTypeEnum> {
                override fun serialize(src: UserTypeEnum, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
                    return JsonPrimitive(src.value)
                }
            })
            .registerTypeAdapter(UserTypeEnum::class.java, object : JsonDeserializer<UserTypeEnum> {
                override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): UserTypeEnum {
                    val value = json.asInt
                    return UserTypeEnum.values().find { it.value == value } ?: UserTypeEnum.GRANDPA
                }
            })
            .create()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConfiguration.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideHomeworkCorrectionApiService(
        retrofit: Retrofit
    ): HomeworkCorrectionApiService {
        return retrofit.create(HomeworkCorrectionApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideLegacyAuthService(retrofit: Retrofit): LegacyAuthService {
        return retrofit.create(LegacyAuthService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideUserManagementService(retrofit: Retrofit): UserManagementService {
        return retrofit.create(UserManagementService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideUploadService(retrofit: Retrofit): UploadService {
        return retrofit.create(UploadService::class.java)
    }

    @Provides
    @Singleton
    fun provideTeacherService(retrofit: Retrofit): TeacherService {
        return retrofit.create(TeacherService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context = context
}
package com.seecolab.istudy.di

import com.seecolab.istudy.data.api.AIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AIModule {
    
    @Provides
    @Singleton
    fun provideAIService(retrofit: Retrofit): AIService {
        return retrofit.create(AIService::class.java)
    }
}
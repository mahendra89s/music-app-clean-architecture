package com.example.domain.di

import com.example.domain.mapper.MusicMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Singleton
    @Provides
    fun providesMapper(): MusicMapper{
        return MusicMapper()
    }
}
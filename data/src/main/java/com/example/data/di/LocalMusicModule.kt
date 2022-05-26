package com.example.data.di

import android.content.Context
import com.example.data.datastore.LocalMusic
import com.example.data.repository.Repository
import com.example.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalMusicModule {

    @Singleton
    @Provides
    fun provideLocalMusic(@ApplicationContext context: Context) : LocalMusic{
        return LocalMusic(context)
    }

    @Singleton
    @Provides
    fun provideRepository(localMusic: LocalMusic) : Repository{
        return RepositoryImpl(localMusic)
    }
}
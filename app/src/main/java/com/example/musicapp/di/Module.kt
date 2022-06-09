package com.example.musicapp.di

import android.content.Context
import com.example.musicapp.utils.MediaPlayer
import com.example.musicapp.utils.Timer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideMediaPlayerClass(@ApplicationContext context: Context): MediaPlayer {
        return MediaPlayer(context)
    }

    @Provides
    @Singleton
    fun provideTimer(): Timer {
        return Timer()
    }
}
package com.example.musicapp.utils

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

@Singleton
class Timer {
    private val _timer = MutableStateFlow<Long>(0)
    var timer = _timer.asStateFlow()

    var whenPaused: Long = 0;
    var time: Long = 0;
    private lateinit var timerCountUp: PlaybackTimer

    operator fun invoke(seconds: Long) {
        time = if (whenPaused.toInt()>0) seconds - whenPaused else seconds

        timerCountUp = object : PlaybackTimer(time) {
            override suspend fun onTicker(second: Long) {
                _timer.update {
                    second + whenPaused
                }
            }
        }
    }

    fun startTime() {
        timerCountUp.start()
    }

    fun pauseTime() {
        whenPaused = _timer.value
        Log.e("whenPause","$whenPaused")
        timerCountUp.cancel()
    }

    fun stopTimer() {
        timerCountUp.cancel()
        _timer.update { 0 }
        whenPaused = 0
    }
}
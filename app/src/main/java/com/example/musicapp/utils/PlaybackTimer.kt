package com.example.musicapp.utils

import android.os.CountDownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


abstract class PlaybackTimer(
    val duration: Long,
) : CountDownTimer(duration,1000){

    override fun onTick(msUntilFinished: Long) {
        val second = duration - msUntilFinished;
        CoroutineScope(Dispatchers.IO).launch {
            onTicker(second)
        }

    }

    override fun onFinish() {
        CoroutineScope(Dispatchers.IO).launch {
            onTicker(duration)
        }
    }

    abstract suspend fun onTicker(second: Long)
}
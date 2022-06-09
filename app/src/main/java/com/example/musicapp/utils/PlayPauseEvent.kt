package com.example.musicapp.utils

sealed class PlayPauseEvent {
    object Play : PlayPauseEvent()
    object Pause : PlayPauseEvent()
    object Stop : PlayPauseEvent()
}
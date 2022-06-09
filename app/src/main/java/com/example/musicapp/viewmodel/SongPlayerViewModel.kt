package com.example.musicapp.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.utils.MediaPlayer
import com.example.musicapp.utils.PlayPauseEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongPlayerViewModel @Inject constructor(
    private val mediaPlayer: MediaPlayer
) : ViewModel() {

    private var _timerEvent = MutableLiveData<PlayPauseEvent>(PlayPauseEvent.Play)
    var timerEvent: LiveData<PlayPauseEvent> = _timerEvent

    fun onPlayPauseButtonClick(view: View) {
        mediaPlayer.playAndPause()
        if (mediaPlayer.isPlaying()) {
            Log.e("Playing", "Playing Song!!!!")
            _timerEvent.value = PlayPauseEvent.Play
        } else {
            Log.e("Paused", "Paused Song!!!!")
            _timerEvent.value = PlayPauseEvent.Pause
        }
    }
}
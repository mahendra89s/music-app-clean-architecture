package com.example.musicapp.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.utils.MediaPlayer
import com.example.musicapp.utils.PlayPauseEvent
import com.example.musicapp.utils.PlaybackTimer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SongPlayerViewModel @Inject constructor(
    private val mediaPlayer: MediaPlayer
) : ViewModel() {

    private var _timerEvent = MutableLiveData<PlayPauseEvent>(PlayPauseEvent.Play)
    var timerEvent: LiveData<PlayPauseEvent> = _timerEvent

//    init {
//        timerEvent()
//    }

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

//    fun timerEvent() {
//        if (mediaPlayer.isPlaying()) {
//            Log.e("Playing", "Playing Song!!!!")
//            _timerEvent.value = PlayPauseEvent.Play
//        } else {
//            Log.e("Paused", "Paused Song!!!!")
//            _timerEvent.value = PlayPauseEvent.Pause
//        }
//        if (mediaPlayer.isCompleted()) {
//            _timerEvent.value = PlayPauseEvent.Stop
//        }
//    }
}
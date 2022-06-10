package com.example.musicapp.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.domain.CurrentSongSession
import com.example.domain.usecase.GetNextPreviousSongUseCase
import com.example.musicapp.mapper.DomainMusicMapper
import com.example.musicapp.model.Music
import com.example.musicapp.utils.MediaPlayer
import com.example.musicapp.utils.PlayPauseEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongPlayerViewModel @Inject constructor(
    private val mediaPlayer: MediaPlayer,
    private val getNextPreviousSongUseCase: GetNextPreviousSongUseCase,
    private val domainMusicMapper: DomainMusicMapper,
    private val currentSongSession : CurrentSongSession
) : ViewModel() {

    private var _timerEvent = MutableLiveData<PlayPauseEvent>(PlayPauseEvent.Play)
    var timerEvent: LiveData<PlayPauseEvent> = _timerEvent

    private var _session : MutableLiveData<Music> = MutableLiveData<Music>()
    val session : LiveData<Music> = _session

    init {
        getSongSession()
    }

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

    fun getNextSong() {
        Log.e("next","song")
        viewModelScope.launch {
            getNextPreviousSongUseCase.getNextSong(session.value?.id!!).collect {
                _session.value = it?.let { music ->
                    domainMusicMapper.mapFromEntity(music)
                }
            }
        }
    }

    fun getPreviousSong() {
        Log.e("previous","song")
        viewModelScope.launch {
            getNextPreviousSongUseCase.getPreviousSong(session.value?.id!!).collect {
                _session.value = it?.let { domainMusic ->
                    domainMusicMapper.mapFromEntity(domainMusic)
                }
            }
        }
    }

    private fun getSongSession(){
        viewModelScope.launch {
            currentSongSession.session.collect{
                Log.e("music2","$it")
                _session.value = it?.let {domainMusic ->
                    domainMusicMapper.mapFromEntity(domainMusic)
                }
            }
        }

    }
}
package com.example.musicapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetSongsFromDeviceUseCase
import com.example.musicapp.mapper.DomainMusicMapper
import com.example.musicapp.model.Music
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSongsFromDeviceUseCase: GetSongsFromDeviceUseCase,
    private val domainMusicMapper: DomainMusicMapper
) : ViewModel() {

    private val _songs = MutableLiveData<List<Music>>()
    private val songs: LiveData<List<Music>> = _songs
    private val songsList = mutableListOf<Music>()

    init {
        viewModelScope.launch {
            getSongsFromDeviceUseCase.invoke().collect {
                songsList.add(domainMusicMapper.mapFromEntity(it))
                _songs.value = songsList
            }
        }
    }

    fun getSongs(): LiveData<List<Music>> = songs
}
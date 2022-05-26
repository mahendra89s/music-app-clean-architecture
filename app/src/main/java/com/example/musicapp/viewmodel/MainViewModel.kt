package com.example.musicapp.viewmodel

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetSongsFromDeviceUseCase
import com.example.musicapp.mapper.DomainMusicMapper
import com.example.musicapp.model.Music
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSongsFromDeviceUseCase : GetSongsFromDeviceUseCase,
    private val domainMusicMapper: DomainMusicMapper
) :ViewModel() {

    private val _songs = MutableLiveData<List<Music>>()
    private val songs : LiveData<List<Music>> = _songs
    val songsList = mutableListOf<Music>()
    init {

        viewModelScope.launch {
//            getSongsFromDeviceUseCase.invoke().forEach{
//                Log.d("view model ---","$it")
//                songsList.add(domainMusicMapper.mapFromEntity(it))
//            }


        }
    }

    fun getSongs() : LiveData<List<Music>> = songs
}
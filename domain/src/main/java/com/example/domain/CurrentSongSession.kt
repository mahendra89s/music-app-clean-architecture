package com.example.domain

import com.example.domain.mapper.MusicMapper
import com.example.domain.model.DomainMusic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentSongSession @Inject constructor(
) {
    private val _session = MutableStateFlow<DomainMusic?>(null)
    var session = _session.asStateFlow()

    fun setSongSession(music : DomainMusic){
        _session.value = music
    }

}
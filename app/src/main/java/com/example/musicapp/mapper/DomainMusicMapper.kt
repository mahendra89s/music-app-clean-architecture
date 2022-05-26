package com.example.musicapp.mapper


import com.example.domain.model.DomainMusic
import com.example.musicapp.model.Music
import javax.inject.Inject

class DomainMusicMapper @Inject constructor() : Mapper<Music, DomainMusic> {
    override fun mapToEntity(type: Music): DomainMusic {
        return DomainMusic(type.id,type.title,type.artist,type.duration,type.data,type.uri)
    }

    override fun mapFromEntity(type: DomainMusic): Music {
        return Music(type.id,type.title,type.artist,type.duration,type.data,type.uri)
    }
}
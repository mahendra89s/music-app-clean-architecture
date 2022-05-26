package com.example.domain.mapper

import com.example.data.model.DataMusic
import com.example.domain.model.DomainMusic
import javax.inject.Inject

class MusicMapper @Inject constructor() : Mapper<DomainMusic,DataMusic> {
    override fun mapToEntity(type: DomainMusic): DataMusic {
        return DataMusic(type.id,type.title,type.artist,type.duration,type.data,type.uri)
    }

    override fun mapFromEntity(type: DataMusic): DomainMusic {
        return DomainMusic(type.id,type.title,type.artist,type.duration,type.data,type.uri)
    }
}
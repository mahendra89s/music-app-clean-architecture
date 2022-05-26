package com.example.domain.usecase

import com.example.data.repository.Repository
import com.example.domain.mapper.MusicMapper
import com.example.domain.model.DomainMusic
import kotlinx.coroutines.flow.forEach
import javax.inject.Inject

class GetSongsFromDeviceUseCase @Inject constructor(
    private val repository : Repository,
    private val musicMapper: MusicMapper
) {
    suspend operator fun invoke() : List<DomainMusic> {
        repository.getSongs().collect{ list ->
           list.forEach {
               musicMapper.mapFromEntity(it)
           }
        }
    }
}
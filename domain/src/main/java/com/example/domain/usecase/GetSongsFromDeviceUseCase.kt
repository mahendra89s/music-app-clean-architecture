package com.example.domain.usecase

import com.example.data.repository.Repository
import com.example.domain.mapper.MusicMapper
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetSongsFromDeviceUseCase @Inject constructor(
    private val repository: Repository,
    private val musicMapper: MusicMapper
) {
    suspend operator fun invoke() = repository.getSongs().transform {
        it.forEach {
            emit(musicMapper.mapFromEntity(it))
        }
    }

}
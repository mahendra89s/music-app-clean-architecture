package com.example.domain.usecase;

import com.example.data.repository.Repository
import com.example.domain.mapper.MusicMapper
import com.example.domain.model.DomainMusic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetNextPreviousSongUseCase @Inject constructor(
    private val repository: Repository,
    private val musicMapper: MusicMapper
) {

    suspend fun getNextSong(id : Long) : Flow<DomainMusic?>{
        return repository.getNextSong(id).transform {
            emit(it?.let { it1 -> musicMapper.mapFromEntity(it1) })
        }
    }

    suspend fun getPreviousSong(id : Long) : Flow<DomainMusic?> {
        return repository.getPreviousSong(id).transform {
            emit(it?.let { it1 -> musicMapper.mapFromEntity(it1) })
        }

    }

}

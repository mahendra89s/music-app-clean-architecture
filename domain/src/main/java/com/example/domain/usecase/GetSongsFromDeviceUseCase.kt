package com.example.domain.usecase

import android.util.Log
import com.example.data.repository.Repository
import com.example.domain.mapper.MusicMapper
import com.example.domain.model.DomainMusic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSongsFromDeviceUseCase @Inject constructor(
    private val repository: Repository,
    private val musicMapper: MusicMapper
) {
    suspend operator fun invoke() = flow {
        repository.getSongs().collect { list ->
            list.forEach {
                emit(musicMapper.mapFromEntity(it))
            }
        }
    }
}
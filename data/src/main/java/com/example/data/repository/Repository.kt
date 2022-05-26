package com.example.data.repository

import com.example.data.model.DataMusic
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    suspend fun getSongs() : StateFlow<List<DataMusic>>
}
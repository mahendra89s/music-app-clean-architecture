package com.example.data.repository

import android.util.Log
import com.example.data.datastore.LocalMusic
import com.example.data.model.DataMusic
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localMusic: LocalMusic
): Repository{
    override suspend fun getSongs() : StateFlow<List<DataMusic>> {
        return localMusic.getSongs()
    }
}
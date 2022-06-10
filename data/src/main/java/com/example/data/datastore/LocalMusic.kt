package com.example.data.datastore

import android.content.ContentUris
import android.content.Context

import android.provider.MediaStore
import android.util.Log
import com.example.data.model.DataMusic
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LocalMusic(
    @ApplicationContext val context: Context
) {
    var songs: MutableList<DataMusic> = mutableListOf<DataMusic>()
    private var songList = MutableStateFlow<List<DataMusic>>(listOf())
    private val _songList: StateFlow<List<DataMusic>>
        get() = songList

    private suspend fun fetchSongFromPhone() {
        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
            )
            val sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC"
            context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val title = it.getString(titleColumn)
                    val artist = it.getString(artistColumn)
                    val duration = it.getLong(durationColumn)
                    val data = it.getString(dataColumn)
                    val contenturi = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    songs.add(DataMusic(id, title, artist, duration, data, contenturi))
                }
                songList.value = songs
            }

        }
        Log.e("Songs :", "${songList.value}")
    }

    suspend fun getSongs(): StateFlow<List<DataMusic>> {
        if (songs.isEmpty()) {
            fetchSongFromPhone()
        }
        return _songList
    }

    private fun getCurrentSong(id: Long): Int {
        val currentSong = songList.value.find {
            it.id == id
        }
        return songList.value.indexOf(currentSong)
    }

    fun fetchNextSong(id: Long) = flow {
        if (getCurrentSong(id) != songList.value.size-1) {
            emit(songList.value[getCurrentSong(id) + 1])
        }
    }

    fun fetchPreviousSong(id: Long) = flow {
        if (getCurrentSong(id) != 0) {
            emit(songList.value[getCurrentSong(id) - 1])
        }
    }
}





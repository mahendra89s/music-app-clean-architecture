package com.example.musicapp.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class MediaPlayer @Inject constructor(
    @ApplicationContext val context: Context
) {
    private var media: MediaPlayer? = null

    fun initializeMediaPlayer(id: Uri) {
        if (media != null) {
            media?.stop()
            media = null
        }
        if (media == null) {
            media = MediaPlayer.create(context, id)
        }
        media?.start()
    }

    fun playAndPause() {
        if (media != null) {
            if (media?.isPlaying!!) {
                media?.pause()
            } else {
                media?.start()
            }
        }
    }

    fun mediaSeekTo(p1: Int) {
        media?.seekTo(p1)
    }

    fun getDuration(): Int {
        return media?.duration!!
    }

    fun getCurrentPosition(): Int {
        return media?.currentPosition!!
    }


    fun isPlaying():Boolean{
        return media?.isPlaying!!
    }

    fun isCompleted(): Boolean{
        var isCompleted = false
        media?.setOnCompletionListener {
            isCompleted = true
        }
        return isCompleted
    }
}
package com.example.musicapp.model

import android.net.Uri

data class Music(
    val id : Long,
    val title : String,
    val artist : String,
    val duration : String,
    val data : String,
    val uri : Uri
)

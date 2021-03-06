package com.example.data.model

import android.net.Uri

data class DataMusic(
    val id : Long,
    val title : String,
    val artist : String,
    val duration : Long,
    val data : String,
    val uri : Uri
)

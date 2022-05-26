package com.example.domain.model

import android.net.Uri

data class DomainMusic(
    val id : Long,
    val title : String,
    val artist : String,
    val duration : String,
    val data : String,
    val uri : Uri
)

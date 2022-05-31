package com.example.musicapp.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Music(
    val id : Long,
    val title : String,
    val artist : String,
    var duration : String,
    val data : String,
    val uri : Uri
) : Parcelable
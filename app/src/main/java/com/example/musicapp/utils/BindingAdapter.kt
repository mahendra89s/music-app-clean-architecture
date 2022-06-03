package com.example.musicapp.utils

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.concurrent.TimeUnit

@BindingAdapter("bind:imageUri")
fun setImageViewSrc(imageView: ImageView,data : Uri){
    val mmr = MediaMetadataRetriever()
    val bfo = BitmapFactory.Options()
    mmr.setDataSource(imageView.context, data)
    val rawArt: ByteArray?= mmr.embeddedPicture
    if (null != rawArt) {
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size, bfo))
    }
}

@BindingAdapter("bind:setTime")
fun setTimeToTextView(textView: TextView, duration : String){
    val hours = TimeUnit.MILLISECONDS.toHours(duration.toLong())
    val minute = TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) % 60
    if(hours.toInt() == 0){
        textView.text = "$minute:$seconds"
    } else{
        textView.text = "$hours:$minute:$seconds"
    }
}
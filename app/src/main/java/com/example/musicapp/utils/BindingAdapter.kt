package com.example.musicapp.utils

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat
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
fun setTimeToTextView(textView: TextView, duration : Long){
    Log.e("duration","$duration")
    val f = DecimalFormat("00")
    val hours = TimeUnit.MILLISECONDS.toHours(duration)
    val minute = TimeUnit.MILLISECONDS.toMinutes(duration)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60
    Log.e("time","$hours $minute $seconds")
    if(hours.toInt() == 0){
        textView.text = "${f.format(minute)}:${f.format(seconds)}"
    } else{
        textView.text = "${f.format(hours)}:${f.format(minute)}:${f.format(seconds)}"
    }
}

fun TextView.setTime(duration: Long){
    val f = DecimalFormat("00")
    val hours = TimeUnit.MILLISECONDS.toHours(duration)
    val minute = TimeUnit.MILLISECONDS.toMinutes(duration)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60
    Log.e("time","$hours $minute $seconds")
    if(hours.toInt() == 0){
        text = "${f.format(minute)}:${f.format(seconds)}"
    } else{
        text = "${f.format(hours)}:${f.format(minute)}:${f.format(seconds)}"
    }

}
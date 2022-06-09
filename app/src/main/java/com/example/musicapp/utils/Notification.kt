package com.example.musicapp.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.NotificationManagerCompat
import com.example.musicapp.R
import com.example.musicapp.model.Music
import com.example.musicapp.service.NotificationActionService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class Notification @Inject constructor(
    @ApplicationContext val context: Context
) {
    private lateinit var builder: Notification.Builder
    private var drw_previous : Int = -1
    private var drw_next : Int = -1
    private var previousPendingIntent: PendingIntent? = null
    private var nextPendingIntent: PendingIntent? = null
    var playPendingIntent : PendingIntent? = null

    companion object{
        const val CHANNEL_ID = "channel1"

        const val ACTION_PREVIOUS = "actionprevious"
        const val ACTION_PLAY = "actionplay"
        const val ACTION_NEXT = "actionnext"
    }

    operator fun invoke(music: Music,pos : Int,size:Int,playButton :Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionCompat = MediaSession(context, "tag")

            val icon = MediaStore.Images.Media.getBitmap(context.contentResolver, music.uri)

            //Previous Action Intent
            if(pos == 0){
                previousPendingIntent = null
                drw_previous = 0
            }else{
                val intentPrevious = Intent(context,NotificationActionService::class.java).setAction(
                    ACTION_PREVIOUS)
                previousPendingIntent = PendingIntent.getBroadcast(context,0,intentPrevious,PendingIntent.FLAG_UPDATE_CURRENT)
                drw_previous = R.drawable.ic_previous
            }

            //Next Action Intent
            if(pos == size){
                previousPendingIntent = null
                drw_next = 0
            }else{
                val intentNext = Intent(context,NotificationActionService::class.java).setAction(
                    ACTION_NEXT)
                nextPendingIntent = PendingIntent.getBroadcast(context,0,intentNext,PendingIntent.FLAG_UPDATE_CURRENT)
                drw_next = R.drawable.ic_next
            }
            //Play Action Intent
            val intentPlay = Intent(context,NotificationActionService::class.java).setAction(
                ACTION_PLAY)
            playPendingIntent = PendingIntent.getBroadcast(context,0,intentPlay,PendingIntent.FLAG_UPDATE_CURRENT)


            builder = Notification.Builder(context)
                .setContentTitle(music.title)
                .setContentText(music.artist)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(icon)
                .addAction(drw_previous,"Previous",previousPendingIntent)
                .addAction(drw_next,"Next",nextPendingIntent)
                .addAction(playButton,"Play",playPendingIntent)
                .setStyle(
                    Notification.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken)
                )
                .setPriority(Notification.PRIORITY_LOW)
        }


    }

}
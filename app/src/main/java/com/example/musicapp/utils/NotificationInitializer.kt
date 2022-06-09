package com.example.musicapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.startup.Initializer
import com.example.musicapp.service.NotificationActionService

class NotificationInitializer : Initializer<Boolean>{

    override fun create(context: Context): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Notification.CHANNEL_ID,
                "Music",
                NotificationManager.IMPORTANCE_LOW
            )
            context.getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(notificationChannel)

            context.registerReceiver(NotificationActionService::class.java,Intent("TRACKS"))
        }
        return true
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}
package com.example.finalproject

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

class BackgroundService : Service(){

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()


        return super.onStartCommand(intent, flags, startId)
    }

    private fun showNotification(){
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

//        val notification = Notification
//            .Builder(this, CHANNEL_ID)
//            .setContentText("Order ready")
//            .setSmallIcon(R.drawable.burger)
//            .setContentText(pendingIntent)
//            .build()
    }

    private fun createNotificationChannel(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//
//            val serviceChannel = NotificationChannel{
//                CHANNEL_ID, "My Service Channel",
//                NotificationManager.IMPORTANCE_DEFAULT
//            }
//
//            val manager = getSystemService{
//                NotificationManager::class.java
//            }
//
//            manager.createNotificationChannel(serviceChannel)
//        }
    }
}
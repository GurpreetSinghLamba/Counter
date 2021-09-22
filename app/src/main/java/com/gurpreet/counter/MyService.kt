package com.gurpreet.counter


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.io.File

class MyService:Service(){
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        shownotification()


        return super.onStartCommand(intent, flags, startId)

    }

    private fun shownotification() {

        val dataDir= ContextCompat.getDataDir(this)
        val myFile= File(dataDir,"file.txt")


        val intentIncrement= Intent(this,NotificationActionReceiver::class.java)
        intentIncrement.setAction("INCREMENT")



        val incrementPendingIntent= PendingIntent.getBroadcast(this, 0, intentIncrement, PendingIntent.FLAG_UPDATE_CURRENT)


        val Notification= NotificationCompat.Builder(this,"first")
                .setContentTitle("Counter")
                .setOnlyAlertOnce(true)
                .setContentText(myFile.readText())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .addAction(R.drawable.ic_launcher_foreground, "INCREMENT", incrementPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        startForeground(1,Notification)
    }

    private fun createNotificationChannel(){
        val nm= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel("first", "ServiceChannel", NotificationManager.IMPORTANCE_DEFAULT))
        }
    }

}
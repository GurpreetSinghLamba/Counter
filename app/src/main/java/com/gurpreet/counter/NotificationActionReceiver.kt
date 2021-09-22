package com.gurpreet.counter

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.io.File


class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals("INCREMENT")) {
            val dataDir= ContextCompat.getDataDir(context)
            val myFile= File(dataDir, "file.txt")
            var count = myFile.readText().toInt()
            count=count+1
            myFile.writeText(count.toString())

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val intentIncrement= Intent(context, NotificationActionReceiver::class.java)
            intentIncrement.setAction("INCREMENT")

            val incrementPendingIntent= PendingIntent.getBroadcast(
                context,
                0,
                intentIncrement,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


            val Notification= NotificationCompat.Builder(context, "first")
                .setContentTitle("Counter")
                .setOnlyAlertOnce(true)
                .setContentText(myFile.readText())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .addAction(R.drawable.ic_launcher_foreground, "INCREMENT", incrementPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            notificationManager.notify(1, Notification)

            val i = Intent("broadCastName")
            i.putExtra("message", myFile.readText())
            context.sendBroadcast(i)
        }
    }
}
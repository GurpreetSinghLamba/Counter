package com.gurpreet.counter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var count:Int= 0
        val dataDir= ContextCompat.getDataDir(this)
        val myFile= File(dataDir, "file.txt")
        if (!myFile.exists()){
            myFile.writeText("0")
        }

        count= myFile.readText().toInt()
        tv.text= count.toString()


        btn.setOnClickListener{
            count+=1
            tv.text= count.toString()

            myFile.writeText(count.toString())
            startService(Intent(this, MyService::class.java))
        }
        val br=broadcastReceiver()
        registerReceiver(br, IntentFilter("broadCastName"))
    }

    inner class broadcastReceiver:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val b = intent?.getStringExtra("message")
            tv.text= b
        }

    }
}
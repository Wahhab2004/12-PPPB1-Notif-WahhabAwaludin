package com.example.pertemuan12

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.pertemuan12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //Untuk value dari channelId & notifId bisa diisi sesuka hati
    //Asalkan value channelId menggunakan string dan notifId menggunakan integer
    private val channelId = "TEST_NOTIF"
    private val notifId = 90
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        binding.kirimSkor.setOnClickListener {
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
            else {
                0
            }
            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                flag
            )
            val remoteView = RemoteViews(packageName, R.layout.custom_notification)
            // Set data untuk notifikasi
            remoteView.setTextViewText(R.id.team_name_left, "Real Madrid")
            remoteView.setTextViewText(R.id.team_name_right, "AC Milan")
            remoteView.setTextViewText(R.id.score, "3 : 1")
            remoteView.setImageViewResource(R.id.logo_team_left, R.drawable.real)
            remoteView.setImageViewResource(R.id.logo_team_right, R.drawable.ac_milan)

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteView)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notifChannel = NotificationChannel(
                    channelId,
                    "Skor Sepak Bola",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notifManager.createNotificationChannel(notifChannel)
            }

            notifManager.notify(notifId, builder.build())

        }
    }
}


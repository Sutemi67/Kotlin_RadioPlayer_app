package apc.appcradle.radioplayer.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.app.CHANNEL_ID

class MediaService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()

        val path = intent?.getStringExtra("path")
        if (path != null) {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(path)
                prepareAsync()
                setOnPreparedListener {
                    it.start()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("MediaService", "Error occurred: what=$what, extra=$extra")
                    stopSelf()
                    true
                }
            }
            ServiceCompat.startForeground(
                this,
                100,
                createNotification(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                } else {
                    0
                },
            )
        }
        return START_STICKY
    }


    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "media_channel"
            val descriptionText = "Media Player"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        // Создаём интент для открытия приложения
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // Создаём PendingIntent для интента
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo3)
            .setContentTitle("Radio Player is active")
            .setContentText("Нажмите для возврата в плеер")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setOngoing(true)
            .setContentIntent(pendingIntent)

        val notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_FOREGROUND_SERVICE
        return notification
    }
}
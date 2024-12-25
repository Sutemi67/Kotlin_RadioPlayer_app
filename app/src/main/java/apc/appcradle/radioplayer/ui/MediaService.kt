package apc.appcradle.radioplayer.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import apc.appcradle.radioplayer.R

class MediaService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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
            val notification = createNotification()
            startForeground(1, notification)
        }
        return START_STICKY
    }


    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroy()
    }

    private fun createNotification(): Notification {
        val channelId = "media_player_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Media Player",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Media Player")
            .setContentText("Playing media...")
            .setSmallIcon(R.drawable.logo_main)
            .build()
    }
}
package apc.appcradle.radioplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.databinding.ActivityMainBinding
import apc.appcradle.radioplayer.databinding.ListItemBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingList: ListItemBinding
    private val mediaPlayer = MediaPlayer()
    private val adapter = RadioAdapter()
    private lateinit var recycler: RecyclerView
    private var playButtonGlobal: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingList = ListItemBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recycler = binding.recycler
        adapter.setData(playlist)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        adapter.setPlayer = object : SetPlayerInterface {
            override fun setPlayer(position: Int, progressBar: View, playButton: ImageView) {
                if (!mediaPlayer.isPlaying) {
                    playButtonGlobal = playButton
                    Log.e("log", "пошла установка плеера")
                    progressBar.isVisible = true
                    playButton.setImageResource(R.drawable.baseline_stop_circle_24)
                    setPlayerStation(playlist[position], progressBar)
                } else {
                    Log.e("log", "плеер не установился")
                    mediaPlayer.pause()
                    playButton.setImageResource(R.drawable.baseline_play_circle_24)
                }
            }
        }
    }

    private fun setPlayerStation(station: Station, progressBar: View) {
        mediaPlayer.reset()
        try {
            bindingList.progress.isVisible = true
            mediaPlayer.apply {
                setDataSource(station.url)
                prepareAsync()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Радио не работает", Toast.LENGTH_SHORT).show()
            Log.e("log", "плеер не настроен")
            progressBar.isVisible = false
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener { mediaPlayer ->
            Log.d("log", "плеер готов")
            progressBar.isVisible = false
            onPlayButtonClick()
        }
    }


    private fun onPlayButtonClick() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            Log.d("log", "плеер стартанул")
        } else {
            mediaPlayer.pause()
            Log.d("log", "остановились")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}

package apc.appcradle.radioplayer.ui

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.domain.SetPlayerInterface
import apc.appcradle.radioplayer.data.Station
import apc.appcradle.radioplayer.data.playlist
import apc.appcradle.radioplayer.databinding.ActivityMainBinding
import apc.appcradle.radioplayer.databinding.ListItemBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingList: ListItemBinding
    private val mediaPlayer = MediaPlayer()
    private val adapter = RadioAdapter()
    private lateinit var recycler: RecyclerView

    private var previousPlayButton: ImageView? = null
    private var previousProgress: View? = null
    private var previousContainer: View? = null
    private var previousPosition: Int? = null
    private var isNight = 1
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        isNight = prefs.getInt("prefs", 2)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingList = ListItemBinding.inflate(layoutInflater)
        setTheme()
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
            override fun setPlayer(
                position: Int,
                progressBar: View,
                playButton: ImageView,
                container: View
            ) {

                if (previousPlayButton != null && previousPosition != position) {
                    previousPlayButton?.setImageResource(R.drawable.baseline_play_circle_24)
                    previousProgress?.isVisible = false
                    previousContainer?.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.normal_shape)
                }
                if (!mediaPlayer.isPlaying || previousPosition != position) {
                    previousContainer?.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.normal_shape)
                    previousPosition = position
                    previousProgress = progressBar
                    previousContainer = container
                    Log.e("log", "пошла установка плеера")
                    progressBar.isVisible = true
                    playButton.setImageResource(R.drawable.baseline_stop_circle_24)
                    container.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.plaing_shape)

                    setPlayerStation(playlist[position], progressBar)
                    previousPlayButton = playButton
                } else {
                    Log.e("log", "плеер не установился")
                    mediaPlayer.pause()
                    previousContainer?.background =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.normal_shape)
                    playButton.setImageResource(R.drawable.baseline_play_circle_24)
                    previousPlayButton = null
                    previousPosition = null
                }
            }
        }
        binding.imageView.setOnClickListener {
            changeNightMode()
        }
    }

    private fun setTheme() {
        when (isNight) {
            2 -> {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.imageView.setImageResource(R.drawable.sun)
            }

            1 -> {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.imageView.setImageResource(R.drawable.nightmode)
            }
        }
    }

    private fun changeNightMode() {
        when (isNight) {
            2 -> {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.imageView.setImageResource(R.drawable.sun)
                prefs.edit().putInt("prefs", 1).apply()
            }

            1 -> {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.imageView.setImageResource(R.drawable.sun)
                prefs.edit().putInt("prefs", 2).apply()
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

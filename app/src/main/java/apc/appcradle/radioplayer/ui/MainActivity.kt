package apc.appcradle.radioplayer.ui

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.databinding.ActivityMainBinding
import apc.appcradle.radioplayer.databinding.ListItemBinding
import apc.appcradle.radioplayer.domain.SetPlayerInterface
import apc.appcradle.radioplayer.domain.models.Station
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingList: ListItemBinding
    private val mediaPlayer = MediaPlayer()
    private val adapter = RadioAdapter()
    private lateinit var recycler: RecyclerView
    private var alreadyClicked = false
    private var previousPosition: Int? = null
    private var isNight = 1
    private lateinit var prefs: SharedPreferences

    private val vm by viewModel<MainViewModel>()

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
        adapter.setData(vm.getPlaylist())
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        adapter.setPlayer = object : SetPlayerInterface {
            override fun setPlayer(
                position: Int,
                progressBar: View,
                playButton: ImageView,
                container: View
            ) {
                if (previousPosition != position) {
                    previousPosition = position
                    alreadyClicked = true
                    Log.d("log", "пошла установка плеера")
                    setPlayerStation(vm.getPlaylist()[position], progressBar)
                } else {
                    if (!mediaPlayer.isPlaying && !alreadyClicked) {
                        Log.d("log", "пошла установка плеера")
                        alreadyClicked = true
                        setPlayerStation(vm.getPlaylist()[position], progressBar)
                    } else {
                        alreadyClicked = false
                        Log.i("log", "плеер остановлен")
                        mediaPlayer.reset()
                    }
                }
            }
        }

        binding.imageView.setOnClickListener {
            changeNightMode()
        }
    }

    private fun setPlayerStation(station: Station, progressBar: View) {
        mediaPlayer.reset()
        mediaPlayer.apply {
            setDataSource(station.url)
            prepareAsync()
            setOnPreparedListener { mediaPlayer ->
                Log.d("log", "плеер готов")
                onPlayButtonClick()
                progressBar.isVisible = false
            }
        }
    }

    private fun onPlayButtonClick() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            Log.i("log", "плеер стартанул")
        } else {
            mediaPlayer.reset()
            Log.i("log", "остановились")
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
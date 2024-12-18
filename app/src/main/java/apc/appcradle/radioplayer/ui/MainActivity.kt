package apc.appcradle.radioplayer.ui

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
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
    private var previousHolder: RadioViewHolder? = null
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
                position: Int, holder: RadioViewHolder
            ) {
                setDefaultHolder(holder)

                if (previousPosition != position) {
                    setDefaultHolder(previousHolder)
                    previousPosition = position
                    alreadyClicked = true
                    previousHolder = holder
                    Log.d("log", "пошла установка плеера")
                    setPlayerStation(vm.getPlaylist()[position], holder)
                } else {
                    if (!mediaPlayer.isPlaying && !alreadyClicked) {
                        Log.d("log", "пошла установка плеера")
                        alreadyClicked = true
                        setPlayerStation(vm.getPlaylist()[position], holder)
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

    private fun setPlayerStation(station: Station, holder: RadioViewHolder) {
        mediaPlayer.reset()
        setPlayingHolder(holder)
        mediaPlayer.apply {
            setDataSource(station.url)
            prepareAsync()
            setOnPreparedListener { mediaPlayer ->
                Log.d("log", "плеер готов")
                mediaPlayer.start()
                holder.getProgressBar().isVisible = false
            }
        }
    }

    fun setDefaultHolder(holder: RadioViewHolder?) {
        holder?.getPlayButton()?.setImageResource(R.drawable.baseline_play_circle_24)
        holder?.getContainer()?.background =
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.normal_shape)
        holder?.getProgressBar()?.isVisible = false

    }

    fun setPlayingHolder(holder: RadioViewHolder) {
        holder.getPlayButton().setImageResource(R.drawable.baseline_stop_circle_24)
        holder.getContainer().background =
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.plaing_shape)
        holder.getProgressBar().isVisible = true
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
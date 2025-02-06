package apc.appcradle.radioplayer.ui

import android.content.ComponentName
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.constants.BG_COLOR_TOKEN
import apc.appcradle.radioplayer.constants.SELECTOR_COLOR_TOKEN
import apc.appcradle.radioplayer.constants.TEXT_COLOR_TOKEN
import apc.appcradle.radioplayer.databinding.ActivityMainBinding
import apc.appcradle.radioplayer.databinding.ListItemBinding
import apc.appcradle.radioplayer.domain.SetOnItemClickListener
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingList: ListItemBinding
    private lateinit var recycler: RecyclerView
    private lateinit var prefs: SharedPreferences

    private val adapter = RadioAdapter()
    private var isNight = 1
    private val vm by viewModel<MainViewModel>()
    private var pos = 0

    private lateinit var controller: MediaController
    private var mediaControllerFuture: ListenableFuture<MediaController>? = null

    private var selectorColor = 0
    private var bgColor = 0
    private var textColor = 0

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

        selectorColor = vm.getSavedColor(SELECTOR_COLOR_TOKEN)
        bgColor = vm.getSavedColor(BG_COLOR_TOKEN)
        textColor = vm.getSavedColor(TEXT_COLOR_TOKEN)

        setColors()

        recycler = binding.recycler
        adapter.setData(vm.getPlaylist())
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        initializeMediaController()

        binding.imageView.setOnClickListener {
            changeNightMode()
        }
        binding.telegramIcon.setOnClickListener {
            vm.openTelegram(this)
        }
        binding.settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        adapter.setOnItemClickListener = object : SetOnItemClickListener {
            override fun setTrack(
                position: Int,
                setSelectorColor: (PlayerState) -> Unit
            ) {
                if (position != pos) {
                    this@MainActivity.pos = position
                    controller.stop()
                    playMedia()
                    setSelectorColor(PlayerState.Playing(selectorColor))
                } else {
                    if (controller.isPlaying) {
                        controller.stop()
                        setSelectorColor(PlayerState.Default())
                    } else {
                        playMedia()
                        setSelectorColor(PlayerState.Playing(selectorColor))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        selectorColor = vm.getSavedColor(SELECTOR_COLOR_TOKEN)
        bgColor = vm.getSavedColor(BG_COLOR_TOKEN)
        textColor = vm.getSavedColor(TEXT_COLOR_TOKEN)

        setColors()
    }
    private fun initializeMediaController() {
        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        mediaControllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        mediaControllerFuture?.apply {
            addListener(Runnable {
                controller = get()
                updateUIWithMediaController(controller)
            }, MoreExecutors.directExecutor())
        }
    }

    private fun playMedia() {
        val currentPlaylist = vm.getPlaylist()
        if (currentPlaylist.isEmpty() || pos !in currentPlaylist.indices) return
        val image = Uri.parse("android.resource://${packageName}/${R.drawable.logo_main}")
        val mediaItem = MediaItem.Builder()
            .setMediaId(currentPlaylist[pos].url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtworkUri(image)
                    .setTitle("Radio Player")
                    .setDisplayTitle(currentPlaylist[pos].name)
                    .build()
            ).build()

        controller.setMediaItem(mediaItem)
        controller.prepare()
        controller.play()
    }

    private fun updateUIWithMediaController(controller: MediaController) {
        controller.addListener(object : Player.Listener {

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo, newPosition: Player.PositionInfo, reason: Int
            ) {
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                    }

                    Player.STATE_READY -> {
                    }

                    Player.STATE_ENDED -> {

                    }

                    Player.STATE_IDLE -> {
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {

            }

        })
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
        mediaControllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        super.onDestroy()
    }


    private fun setColors() {
        if (bgColor == 0) return else binding.main.setBackgroundColor(bgColor)
        if (textColor == 0) return else binding.warningText.setTextColor(textColor)
    }
}
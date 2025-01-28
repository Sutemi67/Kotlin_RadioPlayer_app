package apc.appcradle.radioplayer.ui

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.databinding.ActivityMainBinding
import apc.appcradle.radioplayer.databinding.ListItemBinding
import apc.appcradle.radioplayer.domain.SetPlayerInterface
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingList: ListItemBinding
    private lateinit var recycler: RecyclerView
    private lateinit var prefs: SharedPreferences
    private lateinit var onSetGlobal: (Boolean) -> Unit

    private val adapter = RadioAdapter()
    private var alreadyClicked = false
    private var previousPosition: Int? = null
    private var isNight = 1
    private var positionGlobal = 0
    private val vm by viewModel<MainViewModel>()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startMusicService(positionGlobal, onSetGlobal)
        } else {
            Toast.makeText(this, "Can't start foreground service!", Toast.LENGTH_LONG).show()
        }
    }

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
                onSet: (Boolean) -> Unit
            ) {
                positionGlobal = position
                onSetGlobal = onSet
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                } else {
                    startMusicService(position, onSet)
                }
            }
        }

        binding.imageView.setOnClickListener {
            changeNightMode()
        }
    }

    private fun startMusicService(
        position: Int,
        onSet: (Boolean) -> Unit
    ) {
        if (previousPosition != position) {
            previousPosition = position
            alreadyClicked = true
            val intent = Intent(this@MainActivity, MediaService::class.java)
            stopService(intent)
            intent.putExtra("path", vm.getPlaylist()[position].url)
            ContextCompat.startForegroundService(this@MainActivity, intent)
            onSet(true)
        } else {
            if (!alreadyClicked) {
                Log.d("log", "пошла установка плеера")
                alreadyClicked = true
                val intent = Intent(this@MainActivity, MediaService::class.java)
                intent.putExtra("path", vm.getPlaylist()[position].url)
                ContextCompat.startForegroundService(this@MainActivity, intent)
                onSet(true)
            } else {
                alreadyClicked = false
                Log.i("log", "плеер остановлен")
                val intent = Intent(this@MainActivity, MediaService::class.java)
                stopService(intent)
                onSet(false)
            }
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

}
package apc.appcradle.radioplayer.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
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
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingList: ListItemBinding

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
                holder: RadioViewHolder,
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
                        startService(intent)
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

}
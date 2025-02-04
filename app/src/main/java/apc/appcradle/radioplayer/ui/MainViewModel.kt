package apc.appcradle.radioplayer.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import apc.appcradle.radioplayer.domain.RepositoryInterface
import apc.appcradle.radioplayer.domain.models.Station

class MainViewModel(
    private val repository: RepositoryInterface
) : ViewModel() {

    fun getPlaylist(): List<Station> {
        return repository.getPlaylist()
    }

    fun openTelegram(context: Context) {
        val url = Uri.parse("https://t.me/appcradle")
        val intent = Intent(Intent.ACTION_VIEW, url).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(context, intent, null)
    }
}

package apc.appcradle.radioplayer.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.core.content.edit

class SettingsViewModel(
    private val prefs: SharedPreferences
) : ViewModel() {

    fun getSavedColor(token: String): Int = prefs.getInt(token, 0)

    fun saveColor(token: String, color: Int) {
        prefs.edit { putInt(token, color) }
    }

}
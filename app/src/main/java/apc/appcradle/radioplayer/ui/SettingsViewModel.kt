package apc.appcradle.radioplayer.ui

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel

class SettingsViewModel(
    private val prefs: SharedPreferences
) : ViewModel() {


    fun setViewBackground(view: View, token: String) {
        val color = prefs.getInt(token, 0)
        if (color == 0) return else view.setBackgroundColor(color)
    }

    fun getSavedColor(view: View): Int {
        return prefs.getInt("${view.id}", 0)
    }

    fun saveColor(token: String, color: Int) {
        prefs.edit().putInt(token, color).apply()
    }
}
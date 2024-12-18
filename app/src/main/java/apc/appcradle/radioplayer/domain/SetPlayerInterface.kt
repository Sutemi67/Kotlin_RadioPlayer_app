package apc.appcradle.radioplayer.domain

import android.view.View
import android.widget.ImageView

interface SetPlayerInterface {
    fun setPlayer(position: Int, progressBar: View, stationButton: ImageView, container: View)
}
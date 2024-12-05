package apc.appcradle.radioplayer

import android.view.View
import android.widget.ImageView

interface SetPlayerInterface {
    fun setPlayer(position: Int, progressBar: View, stationButton: ImageView, container: View)
}
package apc.appcradle.radioplayer.domain

import apc.appcradle.radioplayer.ui.RadioViewHolder

interface SetPlayerInterface {
    fun setPlayer(position: Int, progressBar: RadioViewHolder)
}
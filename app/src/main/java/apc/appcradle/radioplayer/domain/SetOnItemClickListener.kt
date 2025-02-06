package apc.appcradle.radioplayer.domain

import apc.appcradle.radioplayer.ui.PlayerState

interface SetOnItemClickListener {
    fun setTrack(
        position: Int,
//        onSet: (Boolean) -> Unit,
        setSelectorColor: (PlayerState) -> Unit
    )
}
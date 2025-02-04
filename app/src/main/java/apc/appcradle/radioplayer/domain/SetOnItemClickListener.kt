package apc.appcradle.radioplayer.domain

interface SetOnItemClickListener {
    fun setTrack(
        position: Int,
        onSet: (Boolean) -> Unit
    )
}
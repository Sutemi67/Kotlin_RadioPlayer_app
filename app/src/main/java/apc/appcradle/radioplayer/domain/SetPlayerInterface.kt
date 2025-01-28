package apc.appcradle.radioplayer.domain

interface SetPlayerInterface {
    fun setPlayer(
        position: Int,
        onSet: (Boolean) -> Unit
    )
}
package apc.appcradle.radioplayer.ui

sealed class PlayerState(
    val isPlaying: Boolean,
    val selectorColor: Int
) {
    class Playing(selectorColor: Int) : PlayerState(true, selectorColor)
    class Default() : PlayerState(false, 0)
}
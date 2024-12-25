package apc.appcradle.radioplayer.ui

sealed interface StationState {
    data class Playing(val position: Int) : StationState
    object Awaiting : StationState
}
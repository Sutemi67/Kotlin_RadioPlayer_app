package apc.appcradle.radioplayer.ui

import android.graphics.drawable.GradientDrawable

sealed class PlayerState(
    val isPlaying: Boolean,
    val gradient: GradientDrawable?
) {
    class Playing(gradient: GradientDrawable) : PlayerState(true, gradient)
    class Default() : PlayerState(false, null)
}
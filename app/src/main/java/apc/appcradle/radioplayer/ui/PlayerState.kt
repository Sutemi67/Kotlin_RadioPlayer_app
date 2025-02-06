package apc.appcradle.radioplayer.ui

import android.graphics.drawable.GradientDrawable

sealed class PlayerState(
    val isPlaying: Boolean,
    val gradient: GradientDrawable?,
    val textColor: Int
) {
    class Playing(gradient: GradientDrawable, textColor: Int) :
        PlayerState(true, gradient, textColor)

    class Default(textColor: Int) : PlayerState(false, null, textColor)
}
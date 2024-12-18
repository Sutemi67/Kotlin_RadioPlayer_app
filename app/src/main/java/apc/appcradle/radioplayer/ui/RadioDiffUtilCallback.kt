package apc.appcradle.radioplayer.ui

import androidx.recyclerview.widget.DiffUtil
import apc.appcradle.radioplayer.domain.models.Station

class RadioDiffUtilCallback : DiffUtil.ItemCallback<Station>() {
    override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
        return oldItem == newItem
    }
}
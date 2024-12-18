package apc.appcradle.radioplayer.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.domain.models.Station

class RadioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val stationName: TextView = itemView.findViewById(R.id.radiostation_name)
    private val stationButton: ImageView = itemView.findViewById(R.id.playButton)
    private val progressBar: View = itemView.findViewById(R.id.progress)
    private val container: View = itemView.findViewById(R.id.main_container)

    fun bind(model: Station) {
        stationName.text = model.name
    }
    fun getProgressBar(): View = progressBar
    fun getPlayButton(): ImageView = stationButton
    fun getContainer(): View = container

}




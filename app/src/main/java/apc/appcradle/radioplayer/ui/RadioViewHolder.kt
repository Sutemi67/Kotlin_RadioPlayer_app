package apc.appcradle.radioplayer.ui

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.domain.models.Station

class RadioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val stationName: TextView = itemView.findViewById(R.id.radiostation_name)
    private val stationButton: ImageView = itemView.findViewById(R.id.playButton)
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress)
    private val container: View = itemView.findViewById(R.id.main_container)

    fun bind(model: Station) {
        Log.e("log","Произошел байнд элемента списка")
        stationName.text = model.name
    }
    fun getProgressBar(): ProgressBar = progressBar
    fun getPlayButton(): ImageView = stationButton
    fun getContainer(): View = container

}




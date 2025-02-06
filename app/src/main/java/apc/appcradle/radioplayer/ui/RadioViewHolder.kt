package apc.appcradle.radioplayer.ui

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.domain.models.Station

class RadioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val stationName: TextView = itemView.findViewById(R.id.radiostation_name)
    val stationButton: ImageView = itemView.findViewById(R.id.playButton)
    val progressBar: ProgressBar = itemView.findViewById(R.id.progress)
    val container: View = itemView.findViewById(R.id.main_container)


    fun bind(
        model: Station,
        isThisAPreviousPosition: Boolean,
        color: GradientDrawable?
    ) {
        stationName.text = model.name

        if (isThisAPreviousPosition) {
            stationButton.setImageResource(R.drawable.baseline_stop_circle_24)
            container.background = color
        } else {
            stationButton.setImageResource(R.drawable.baseline_play_circle_24)
            container.setBackgroundColor(itemView.context.getColor(R.color.transparent))
        }
    }
}




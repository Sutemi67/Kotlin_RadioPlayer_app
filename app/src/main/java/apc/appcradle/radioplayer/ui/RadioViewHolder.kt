package apc.appcradle.radioplayer.ui

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
        isThisAPreviousPosition: Boolean
    ) {
        stationName.text = model.name

        if (isThisAPreviousPosition) {
            stationButton.setImageResource(R.drawable.baseline_stop_circle_24)
            container.background =
                ContextCompat.getDrawable(this.container.context, R.drawable.plaing_shape)
        } else {
            stationButton.setImageResource(R.drawable.baseline_play_circle_24)
            container.background =
                ContextCompat.getDrawable(this.container.context, R.drawable.normal_shape)
            progressBar.isVisible = false
        }
    }
}




package apc.appcradle.radioplayer.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.domain.SetPlayerInterface
import apc.appcradle.radioplayer.domain.models.Station

class RadioAdapter() : ListAdapter<Station, RadioViewHolder>(RadioDiffUtilCallback()) {
    var setPlayer: SetPlayerInterface? = null
    private var playingPosition: Int? = null

    private val difUtil = RadioDiffUtilCallback()
    private val asyncListDiffer = AsyncListDiffer(this, difUtil)

    fun setData(list: List<Station>) = asyncListDiffer.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RadioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return RadioViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RadioViewHolder,
        position: Int
    ) {
        val progressBar = holder.getProgressBar()
        val stationButton = holder.getPlayButton()
        val container = holder.getContainer()

        holder.bind(asyncListDiffer.currentList[position])

        setActiveStationUi(holder, position)

        holder.itemView.setOnClickListener {
            determinePreviousPosition(holder, position)
            Log.d("log", "нажал на установку плеера, ${progressBar.id}")
            setPlayer?.setPlayer(holder.adapterPosition, progressBar, stationButton, container)
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    private fun determinePreviousPosition(holder: RadioViewHolder, position: Int) {
        if (playingPosition == position) {
            playingPosition = null
            notifyItemChanged(position)
        } else {
            val previousPosition = playingPosition
            playingPosition = holder.adapterPosition
            notifyItemChanged(previousPosition ?: -1)
            notifyItemChanged(position)
        }
    }

    private fun setActiveStationUi(holder: RadioViewHolder, position: Int) {
        if (playingPosition == position) {
            holder.getPlayButton().setImageResource(R.drawable.baseline_stop_circle_24)
            holder.getContainer().background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.plaing_shape)
            holder.getProgressBar().isVisible = true
        } else {
            holder.getPlayButton().setImageResource(R.drawable.baseline_play_circle_24)
            holder.getContainer().background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.normal_shape)
            holder.getProgressBar().isVisible = false
        }
    }
}
package apc.appcradle.radioplayer.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.domain.SetPlayerInterface
import apc.appcradle.radioplayer.domain.models.Station

class RadioAdapter() : ListAdapter<Station, RadioViewHolder>(RadioDiffUtilCallback()) {
    var setPlayer: SetPlayerInterface? = null
    private var previousPosition: Int = RecyclerView.NO_POSITION
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
        holder.bind(
            asyncListDiffer.currentList[position],
            previousPosition == position
        )
        holder.itemView.setOnClickListener {
            setPlayer?.setPlayer(position, holder) { ui(it, holder, position) }
        }
    }

    private fun ui(state: Boolean, holder: RadioViewHolder, position: Int) {
        if (state) {
            holder.stationButton.setImageResource(R.drawable.baseline_stop_circle_24)
            holder.container.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.plaing_shape)
            notifyItemChanged(position)
            notifyItemChanged(previousPosition)
            previousPosition = holder.getAbsoluteAdapterPosition()
        } else {
            holder.stationButton.setImageResource(R.drawable.baseline_play_circle_24)
            holder.container.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.normal_shape)
            holder.progressBar.isVisible = false
            notifyItemChanged(position)
            notifyItemChanged(previousPosition)
            previousPosition = RecyclerView.NO_POSITION
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

}
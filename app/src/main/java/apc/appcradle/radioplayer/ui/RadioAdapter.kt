package apc.appcradle.radioplayer.ui

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.domain.SetOnItemClickListener
import apc.appcradle.radioplayer.domain.models.Station

class RadioAdapter() : ListAdapter<Station, RadioViewHolder>(RadioDiffUtilCallback()) {
    var setOnItemClickListener: SetOnItemClickListener? = null
    private var previousPosition: Int = RecyclerView.NO_POSITION
    private val difUtil = RadioDiffUtilCallback()
    private val asyncListDiffer = AsyncListDiffer(this, difUtil)
    private var gradient: GradientDrawable? = null

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
            previousPosition == position,
            gradient
        )

        holder.itemView.setOnClickListener {
            setOnItemClickListener?.setTrack(position) {
                gradient = it.gradient
                ui(it, holder, position)
            }
        }

    }

    private fun ui(state: PlayerState, holder: RadioViewHolder, position: Int) {
        when (state) {
            is PlayerState.Default -> {
                holder.progressBar.isVisible = false
                notifyItemChanged(position)
                notifyItemChanged(previousPosition)
                previousPosition = RecyclerView.NO_POSITION
            }

            is PlayerState.Playing -> {
                holder.stationButton.setImageResource(R.drawable.baseline_stop_circle_24)
                notifyItemChanged(position)
                notifyItemChanged(previousPosition)
                previousPosition = holder.getAbsoluteAdapterPosition()
            }
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

}
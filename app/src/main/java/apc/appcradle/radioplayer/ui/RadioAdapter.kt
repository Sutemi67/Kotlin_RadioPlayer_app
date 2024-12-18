package apc.appcradle.radioplayer.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
        holder.bind(asyncListDiffer.currentList[position])

        holder.itemView.setOnClickListener {
            Log.d("log", "нажал на установку плеера")
            setPlayer?.setPlayer(position, holder)
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

//    private fun setActiveStationUi(holder: RadioViewHolder, position: Int) {
//        Log.e("log", "Обновление интерфейса")
//        if (playingPosition != position) {
////            notifyItemChanged(playingPosition ?: -1)
//            holder.getPlayButton().setImageResource(R.drawable.baseline_stop_circle_24)
//            holder.getContainer().background =
//                ContextCompat.getDrawable(holder.itemView.context, R.drawable.plaing_shape)
//            holder.getProgressBar().isVisible = true
//            playingPosition = position
//        } else {
//            holder.getPlayButton().setImageResource(R.drawable.baseline_play_circle_24)
//            holder.getContainer().background =
//                ContextCompat.getDrawable(holder.itemView.context, R.drawable.normal_shape)
//            holder.getProgressBar().isVisible = false
//            playingPosition = null
//        }
//    }
}
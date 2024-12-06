package apc.appcradle.radioplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

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

class RadioAdapter : ListAdapter<Station, RadioViewHolder>(RadioDiffUtilCallback()) {
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
        val currentList = asyncListDiffer.currentList
        holder.bind(currentList[position])

        if (playingPosition == position) {
            holder.getPlayButton().setImageResource(R.drawable.baseline_stop_circle_24)
            holder.getContainer().background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.plaing_shape)
        } else {
            holder.getPlayButton().setImageResource(R.drawable.baseline_play_circle_24)
            holder.getContainer().background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.normal_shape)
        }

        holder.itemView.setOnClickListener {
            val progressBar = holder.getProgressBar()
            val stationButton = holder.getPlayButton()
            val container = holder.getContainer()

            if (playingPosition == position) {
                playingPosition = null
                notifyItemChanged(position)
            } else{
//            if (playingPosition != position) {
                val previousPosition = playingPosition
                playingPosition = position
                notifyItemChanged(previousPosition ?: -1)
                notifyItemChanged(position)
//                setPlayer?.setPlayer(position, progressBar, stationButton, container)
            }
            setPlayer?.setPlayer(position, progressBar, stationButton, container)
        }
    }

    override fun getItemCount(): Int = playlist.size
}

class RadioDiffUtilCallback : DiffUtil.ItemCallback<Station>() {
    override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
        return oldItem == newItem
    }
}
package apc.appcradle.radioplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RadioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val stationName: TextView = itemView.findViewById(R.id.radiostation_name)
    private val stationButton: ImageView = itemView.findViewById(R.id.playButton)
    private val progressBar: View = itemView.findViewById(R.id.progress)

    fun bind(model: Station) {
        stationName.text = model.name
    }

    fun getProgressBar(): View = progressBar
    fun getPlayButton(): ImageView = stationButton
}

class RadioAdapter : ListAdapter<Station, RadioViewHolder>(RadioDiffUtilCallback()) {
    var setPlayer: SetPlayerInterface? = null

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
        holder.itemView.setOnClickListener {
            val progressBar = holder.getProgressBar()
            val stationButton = holder.getPlayButton()
            setPlayer?.setPlayer(position, progressBar, stationButton)
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
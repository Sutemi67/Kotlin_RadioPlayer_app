package apc.appcradle.radioplayer.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
        Log.d("log", "отрисовка позиции $position")

        holder.itemView.setOnClickListener {
            setPlayer?.setPlayer(position, holder)
            notifyItemChanged(position)
            Log.i("log", "перерисовываю позицию $position")
            notifyItemChanged(previousPosition)
            Log.i("log", "перерисовываю позицию $previousPosition")
            previousPosition = holder.adapterPosition
        }
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

}
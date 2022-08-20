package com.sleepTrackerWihtRecyclerview.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameudacity.R
import com.example.gameudacity.databinding.ListItemSleepNightBinding
import com.sleepTrackerWihtRecyclerview.Room.TableEntity
import com.sleepTrackerWihtRecyclerview.convertDurationToFormatted
import com.sleepTrackerWihtRecyclerview.convertNumericQualityToString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0 //represents the header
private const val ITEM_VIEW_TYPE_ITEM = 1 //represent the data items

class SleepNightAdapter(val clickListener: SleepNightListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw  ClassCastException("Unknown view Type ${viewType}")
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }



    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> {
                ITEM_VIEW_TYPE_HEADER
            }
            is DataItem.SleepNightItem -> {
                ITEM_VIEW_TYPE_ITEM
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<TableEntity>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }


    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) :
        RecyclerView.ViewHolder(binding.root) {


//        init {
//          rootclicker()
//        }
//        fun rootclicker(clickListener: SleepNightListener, item: TableEntity){
//            binding.apply {
//                root.setOnClickListener {
//                    val position = adapterPosition
//                    if(position != RecyclerView.NO_POSITION){
//                        clickListener.onClick(item)
//                    }
//
//                }
//            }
//        }

        fun bind(item: TableEntity, clickListener: SleepNightListener) {

            binding.root.setOnClickListener {
                clickListener.onClick(item)
            }

            val res = itemView.context.resources
            binding.sleepLength.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setBackgroundResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }


}

/**refreshing data== DiffUUtil is recommended over notifyDataset changed */

class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}


/**listener class == this class will listen for clicks and pass on the related data for processing those click to the fragment*
 * NB==There are a lot of patterns to implement click listener this is just one of them */
class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: TableEntity) = clickListener(night.nightId)
}

/**data holder class that represents a header*/
sealed class DataItem {
    abstract val id: Long

    data class SleepNightItem(val sleepNight: TableEntity) : DataItem() {
        override val id: Long
            get() = sleepNight.nightId
    }

    object Header : DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }


}

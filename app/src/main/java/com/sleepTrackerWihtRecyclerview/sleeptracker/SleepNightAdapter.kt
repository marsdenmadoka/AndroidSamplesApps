package com.sleepTrackerWihtRecyclerview.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameudacity.R
import com.example.gameudacity.databinding.ListItemSleepNightBinding
import com.sleepTrackerWihtRecyclerview.Room.TableEntity
import com.sleepTrackerWihtRecyclerview.convertDurationToFormatted
import com.sleepTrackerWihtRecyclerview.convertNumericQualityToString


class SleepNightAdapter : ListAdapter<TableEntity, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TableEntity) {
            val res = itemView.context.resources
            binding.sleepLength.text = convertDurationToFormatted(item.startTimeMilli,item.endTimeMilli,res)
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

class SleepNightDiffCallback : DiffUtil.ItemCallback<TableEntity>() {
    override fun areItemsTheSame(oldItem: TableEntity, newItem: TableEntity): Boolean {

        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: TableEntity, newItem: TableEntity): Boolean {
        return oldItem == newItem
    }

}
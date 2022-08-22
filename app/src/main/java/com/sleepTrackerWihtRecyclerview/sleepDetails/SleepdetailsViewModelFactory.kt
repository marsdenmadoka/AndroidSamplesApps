package com.sleepTrackerWihtRecyclerview.sleepDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sleepTrackerWihtRecyclerview.Room.roomDao

class SleepdetailsViewModelFactory(private val sleepNightKey: Long,
                                   private val dataSource: roomDao) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepdetailsViewModel::class.java)) {
            return SleepdetailsViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


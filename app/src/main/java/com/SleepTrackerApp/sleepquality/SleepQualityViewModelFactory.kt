package com.SleepTrackerApp.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.SleepTrackerApp.Room.roomDao


class SleepQualityViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource:roomDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(sleepNightKey,dataSource) as T
        }
        throw IllegalAccessException("Unknown viewModel claass")
    }
}
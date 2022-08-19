package com.sleepTrackerWihtRecyclerview.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.SleepTrackerApp.Room.roomDao


class SleepQualityViewModelFactoryForRecyclerview(
    private val sleepNightKey: Long,
    private val dataSource:roomDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModelForRecyclerview::class.java)) {
            return SleepQualityViewModelForRecyclerview(sleepNightKey,dataSource) as T
        }
        throw IllegalAccessException("Unknown viewModel claass")
    }
}
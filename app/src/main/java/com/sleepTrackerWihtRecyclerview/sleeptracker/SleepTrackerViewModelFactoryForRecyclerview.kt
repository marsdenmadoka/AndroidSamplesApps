package com.sleepTrackerWihtRecyclerview.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sleepTrackerWihtRecyclerview.Room.roomDao


//for basic dependency injection
class SleepTrackerViewModelFactoryForRecyclerview(
    private val dbdao: roomDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackerViewModelForRecyclerview::class.java)) {
            return SleepTrackerViewModelForRecyclerview(dbdao,application) as T
        }
        throw IllegalAccessException("Unknown viewModel claass")
    }
}



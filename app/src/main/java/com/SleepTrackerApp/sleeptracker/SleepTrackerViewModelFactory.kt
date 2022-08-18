package com.SleepTrackerApp.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.SleepTrackerApp.Room.roomDao

class SleepTrackerViewModelFactory(
    private val dbdao: roomDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(dbdao,application) as T
        }
        throw IllegalAccessException("Unknown viewModel claass")
    }
}



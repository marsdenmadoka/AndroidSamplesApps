package com.SleepTrackerApp.sleepquality

import androidx.lifecycle.ViewModel
import com.SleepTrackerApp.Room.roomDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class SleepQualityViewModel(
    private val sleepNightKey: Long = 0L,
    val database: roomDao
) : ViewModel() {

    private val viewModelJob = Job()


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope =
        CoroutineScope(Dispatchers.Main + viewModelJob)//scope for our courintine to run in


}
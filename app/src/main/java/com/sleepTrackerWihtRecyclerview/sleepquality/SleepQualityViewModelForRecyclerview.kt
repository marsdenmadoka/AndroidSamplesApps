package com.sleepTrackerWihtRecyclerview.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.SleepTrackerApp.Room.roomDao
import kotlinx.coroutines.*

class SleepQualityViewModelForRecyclerview(
    private val sleepNightKey: Long = 0L, //sleep night key that we got from the navigation
    val dbdao: roomDao
) : ViewModel() {
    private val viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope =
        CoroutineScope(Dispatchers.Main + viewModelJob)//scope for our courintine to run in

    //navigate back to the sleepTracker after we record the quality
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val tonight = dbdao.get(sleepNightKey) ?: return@withContext//get tonight using the sleepNight key
                tonight.sleepQuality = quality //set the sleep quality
                dbdao.update(tonight)//update the database
            }
            _navigateToSleepTracker.value=true //trigger navigation back to sleepTracker fragment
        }
    }


}
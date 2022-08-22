package com.sleepTrackerWihtRecyclerview.sleepDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sleepTrackerWihtRecyclerview.Room.TableEntity
import com.sleepTrackerWihtRecyclerview.Room.roomDao
import kotlinx.coroutines.Job


class SleepdetailsViewModel(
    private val sleepNightKey: Long = 0L,
    val dataSource: roomDao
) : ViewModel() {
    val database = dataSource

    private val viewModelJob = Job()
  val night = MediatorLiveData<TableEntity>()

    //fun getNight() = night

    /** by right we should be using @Parcelize to get item with a single id
     * but since we did not create a @Parcelize we use this method
     * */
    init {
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
    }

    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }





    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
}
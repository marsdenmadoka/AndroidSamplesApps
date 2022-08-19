package com.sleepTrackerWihtRecyclerview.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sleepTrackerWihtRecyclerview.Room.TableEntity
import com.sleepTrackerWihtRecyclerview.formatNights
import com.sleepTrackerWihtRecyclerview.Room.roomDao
import kotlinx.coroutines.*

class SleepTrackerViewModelForRecyclerview(
    val dbdao: roomDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()//to manage all our Coroutines

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope =
        CoroutineScope(Dispatchers.Main + viewModelJob)//scope for our courintine to run in

    private var tonight = MutableLiveData<TableEntity?>()

    private val nights =
        dbdao.getAllNights()//get all nights from the database not a suspend function since our livedata in the dao runs inside a cournitine secretly

    //transform night into formated string for it to look nice

    val nightsString = Transformations.map(nights) {
        formatNights(it, application.resources)
    }
 /** //button visibility state
    val StartButtonVisible = Transformations.map(tonight){
        null == it
    }
    val StopButtonVisible = Transformations.map(tonight){
        null !=it
    }
    val ClearButtonVisible = Transformations.map(nights){
        it?.isNotEmpty()
    }
    **/

    /**navigation event state*/
    private val _navigateToSleepQuality = MutableLiveData<TableEntity>()
    val navigateToSleepQuality:LiveData<TableEntity>
    get() = _navigateToSleepQuality

    fun doneNavigating(){
        _navigateToSleepQuality.value = null
    }


    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch { //get the value for tonight in the database
            tonight.value = getTonightFromDatabase()
        }
    }

    /**making a request from the database must a suspend function //we put it in a repository if when had created it
     * Returns the latest night saved in the database*/
    private suspend fun getTonightFromDatabase(): TableEntity? {
        return withContext(Dispatchers.IO) {
            var night = dbdao.getTonight()
            //If end time and start time are the same then we know we ar continuing with an exisiting night
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    /**create a new sleep night insert it into the database and assign it to tonight*/
    fun onStartTracking() {
        uiScope.launch {
            val newNight = TableEntity()//capture the current time as a start time
            insertNight(newNight) //insert it to the database
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insertNight(night: TableEntity) {
        withContext(Dispatchers.IO) {
            dbdao.insert(night)
        }
    }


    fun onStoptracking() {//for stop button //stop tracking
        /**executed when the STOP button clicked
         * In kotlin the return@label syntax is used for specifying which function among
         * several nested ones this statement returns from.
         * in this case,we are specifying to return from launch
        * */
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            //update the night in the database to add the end time
            updateNight(oldNight)
            /**using this for navigation,:: setting it to to oldnight since this value
             * is nonNull only when we can set a sleepQuality;if we don't set we cannot navigate **/
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun updateNight(night: TableEntity) {
        withContext(Dispatchers.IO) {
            dbdao.update(night)
        }
    }


    fun onClear() {
        uiScope.launch {
            clearNight()
            tonight.value = null
        }
    }

    suspend fun clearNight() {
        withContext(Dispatchers.IO) {
            dbdao.clear()
        }
    }
}



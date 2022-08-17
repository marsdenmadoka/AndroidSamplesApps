package com.SleepTrackerApp.sleeptracker

import android.text.method.TextKeyListener.clear
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.SleepTrackerApp.Room.TableEntity
import com.SleepTrackerApp.Room.roomDao
import kotlinx.coroutines.*

class SleepTrackerViewModel(val dbdao: roomDao) : ViewModel() {

    private var viewModelJob = Job()//to manage all our Coroutines

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)//scope for our courintine to run in

    private var tonight = MutableLiveData<TableEntity?>()

    private val nights = dbdao.getAllNights()//get all nights from the database not a suspend function since our livedata in the dao runs inside a cournitine secretly

 //transform night into formated string for it to look nice
 val string


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
    fun onStartTracking(){
  uiScope.launch {
      val newNight = TableEntity()//capture the current time as a start time
      insertNight(newNight) //insert it to the database
      tonight.value = getTonightFromDatabase()
  }
    }

    private suspend fun insertNight(night:TableEntity){
        withContext(Dispatchers.IO){
            dbdao.insert(night)
        }
    }


    fun onStoptracking(){//for stop button //stop tracking
        uiScope.launch {
            val oldNight = tonight.value?:return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            updateNight(oldNight)
        }
    }

    private suspend fun updateNight(night: TableEntity){
        withContext(Dispatchers.IO){
            dbdao.update(night)
        }
    }


    fun onClear(){
        uiScope.launch {
            clearNight()
            tonight.value = null
        }
    }

    suspend fun  clearNight(){
        withContext(Dispatchers.IO){
            dbdao.clear()
        }
    }
}



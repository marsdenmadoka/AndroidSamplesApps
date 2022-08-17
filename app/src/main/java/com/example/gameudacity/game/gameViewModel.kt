package com.example.gameudacity.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class gameViewModel : ViewModel() {
    /**Mock viewModel*/
    companion object {  //These represents different important times in the game,such as game length
        //This is when the game is over
        private const val DONE = 0L
        //this is the number of milliseconds in a second
        private const val ONE_SECOND = 1000L
        //this is the total time of the game
        private const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeStrng = Transformations.map(currentTime, { time ->
        DateUtils.formatElapsedTime(time)
    })

    /**the current word*/
    private val _word = MutableLiveData<String>()//var word = ""
    val word: LiveData<String>
        get() = _word

    /**the current score*/
    private val _score = MutableLiveData<Int>() // backing property for purpose of enscapulation
    val score: LiveData<Int>
        get() = _score

    /**creating a livedata boolean that will help us check if our game is finished
     * it does not fetch data from anywhere*/
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish


    //The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinish.value = false

        //creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(p0: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }
    }

    /**Resets the list of words and randomise the order*/
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk"
        )
        wordList.shuffle()
    }

    /**moves to the next word in the list*/
    private fun nextWord() {
        /**select and remove a word from list*/
        if (wordList.isEmpty()) {

            _eventGameFinish.postValue(true)
            resetList()
        }
        _word.value = wordList.removeAt(0)

    }

    /**Methods for buttons press*/
    fun onSkip() {
        /**when skip button pressed*/
        // score--
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        /**when  button pressed*/
        _score.value = (_score.value)?.plus(1)  //score.value +1
        // score++
        nextWord()
    }

    //we want to use live data to represent the state of an event
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

}
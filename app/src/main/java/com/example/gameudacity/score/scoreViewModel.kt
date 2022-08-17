package com.example.gameudacity.score

import android.util.Log
import androidx.lifecycle.ViewModel

class scoreViewModel(finalScore:Int) : ViewModel() {

    init {
        Log.i("score viewModel","final Score is $finalScore")
    }
}
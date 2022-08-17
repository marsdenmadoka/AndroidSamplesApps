package com.example.gameudacity.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class scoreViewModelFactory(private val finalScore:Int):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(scoreViewModel::class.java)){
            return scoreViewModel(finalScore) as T
        }
        throw IllegalAccessException("Unknown viewModel claass")
    }


}
package com.example.gameudacity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gameudacity.game.gameViewModel

class MainActivity : AppCompatActivity() {

   // private lateinit var viewModel: gameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
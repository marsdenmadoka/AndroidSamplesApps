package com.example.gameudacity.score

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentScoreBinding

class scoreFragment : Fragment(R.layout.fragment_score) {
    private lateinit var viewModel: scoreViewModel
    private lateinit var viewModelFactory: scoreViewModelFactory
    private lateinit var binding: FragmentScoreBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScoreBinding.bind(view)

        // viewModelFactory = scoreViewModelFactory(scoreFragmentArgs.fromBundle(arguments).score) //same as the line one below with null checks
        viewModelFactory = arguments?.let { scoreFragmentArgs.fromBundle(it).score }
            ?.let { scoreViewModelFactory(it) }!!
        viewModel = ViewModelProvider(this, viewModelFactory).get(scoreViewModel::class.java)


        binding.textScore.text =
            arguments?.let { scoreFragmentArgs.fromBundle(it).score.toString() }


        binding.playAgain.setOnClickListener { onPlayAgain() }


    }

    private fun onPlayAgain() {

        //  findNavController().navigate(ScoreFragmentDirections.actionRestart())
    }

}
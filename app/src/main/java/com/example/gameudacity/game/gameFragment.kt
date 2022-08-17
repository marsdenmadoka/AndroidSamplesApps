package com.example.gameudacity.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentGameBinding


class gameFragment : Fragment(R.layout.fragment_game) {
    private val viewModel: gameViewModel by viewModels()
 private lateinit var binding:FragmentGameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)

        binding.gotItButton.setOnClickListener {
            viewModel.onCorrect()
        }

        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }


        viewModel.currentTime.observe(viewLifecycleOwner, Observer {
            binding.curentimeText.text = DateUtils.formatElapsedTime(it)
        })

        viewModel.score.observe(viewLifecycleOwner, Observer {
            //update score
            binding.scoreText.text = it.toString()   //viewModel.score.toString()
        })

        viewModel.word.observe(viewLifecycleOwner, Observer {
            //update word
            binding.wordText.text = it
        })


        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) { //check the value of hasFinish
                gameFinished()
                viewModel.onGameFinishComplete() //setting _eventGameFinish.value to false after compeliton once the taost has been show so as to avoid our toast showing all time when scrren rotates
            }
        })


        viewModel.currentTimeStrng.observe(viewLifecycleOwner, Observer {
            binding.curentimeText.text = it
        })
    }

    /**called when the game is finished*/
    private fun gameFinished() {
        val action=gameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0)
           //action.setScore(viewModel.score.value ?: 0) //using annoymmus function to pass arguments
        findNavController().navigate(action)
    }


}

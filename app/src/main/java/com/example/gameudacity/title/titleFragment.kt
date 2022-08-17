package com.example.gameudacity.title

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentGameBinding
import com.example.gameudacity.databinding.FragmentTitleBinding

class titleFragment :Fragment(R.layout.fragment_title){

    private lateinit var binding: FragmentTitleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTitleBinding.bind(view)

        binding.playButton.setOnClickListener {
            findNavController().navigate(titleFragmentDirections.actionTitleFragmentToGameFragment())
        }
    }
}
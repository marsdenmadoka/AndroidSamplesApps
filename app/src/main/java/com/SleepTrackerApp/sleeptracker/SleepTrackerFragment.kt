package com.SleepTrackerApp.sleeptracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentSleepTrackerBinding

class SleepTrackerFragment : Fragment(R.layout.fragment_sleep_tracker) {

    private lateinit var viewModel: SleepTrackerViewModel

    private lateinit var binding: FragmentSleepTrackerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepTrackerBinding.bind(view)

        binding.startTrackingbtn.setOnClickListener {
            viewModel.onStartTracking()
        }

        binding.StopTrackingbtn.setOnClickListener {
            viewModel.onStoptracking()
        }

        binding.clearbtn.setOnClickListener {
            viewModel.onClear()
        }


    }
}
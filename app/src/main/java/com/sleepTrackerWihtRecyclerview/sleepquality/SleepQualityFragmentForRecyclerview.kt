package com.sleepTrackerWihtRecyclerview.sleepquality

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.SleepTrackerApp.Room.roomdatabase
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentSleepQualityforRecyclerviewBinding

class SleepQualityFragmentForRecyclerview :
    Fragment(R.layout.fragment_sleep_qualityfor_recyclerview) {

    private lateinit var binding: FragmentSleepQualityforRecyclerviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepQualityforRecyclerviewBinding.bind(view)

        val application = requireNotNull(this.activity).application

        //getting the arguments that came with navigation
        val arguments = SleepQualityFragmentForRecyclerviewArgs.fromBundle(arguments!!)

        val datasource = roomdatabase.getInstance(application).roomDao

        val viewModelFactory = SleepQualityViewModelFactoryForRecyclerview(arguments.sleepNightKey, datasource)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(SleepQualityViewModelForRecyclerview::class.java)

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(

                    SleepQualityFragmentForRecyclerviewDirections.actionSleepQualityFragmentForRecyclerviewToSleepTrackerFragmentForRecyclerview()

                )
                viewModel.doneNavigating()
            }
        })


        binding.button0.setOnClickListener {
            viewModel.onSetSleepQuality(0)
        }
        binding.button1.setOnClickListener {
            viewModel.onSetSleepQuality(1)
        }
        binding.button2.setOnClickListener {
            viewModel.onSetSleepQuality(2)
        }
        binding.button3.setOnClickListener {
            viewModel.onSetSleepQuality(3)
        }
        binding.button4.setOnClickListener {
            viewModel.onSetSleepQuality(4)
        }
        binding.button5.setOnClickListener {
            viewModel.onSetSleepQuality(5)
        }
    }
}
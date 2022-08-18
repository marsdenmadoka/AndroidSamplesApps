package com.SleepTrackerApp.sleeptracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.SleepTrackerApp.Room.roomdatabase
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentSleepTrackerBinding

class SleepTrackerFragment : Fragment(R.layout.fragment_sleep_tracker) {

//    private lateinit var viewModel: SleepTrackerViewModel
//    private lateinit var viewModelFactory: SleepTrackerViewModelFactory

    private lateinit var binding: FragmentSleepTrackerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepTrackerBinding.bind(view)


        val application = requireNotNull(this.activity).application
        val datasource = roomdatabase.getInstance(application).roomDao
        val viewModelFactory = SleepTrackerViewModelFactory(datasource,application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SleepTrackerViewModel::class.java)


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


        viewModel.nightsString.observe(viewLifecycleOwner, Observer {
            //update word
            binding.showTrackingData.text = it.toString()
        })

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(it.nightId)) //parameter sleepnigt ke
                viewModel.doneNavigating()
            }

        })


    }
}
package com.sleepTrackerWihtRecyclerview.sleeptracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentSleepTrackerForRecyclerviewBinding
import com.sleepTrackerWihtRecyclerview.Room.roomdatabase

class SleepTrackerFragmentForRecyclerview : Fragment(R.layout.fragment_sleep_tracker_for_recyclerview) {

//    private lateinit var viewModel: SleepTrackerViewModel
//    private lateinit var viewModelFactory: SleepTrackerViewModelFactory

    private lateinit var binding:FragmentSleepTrackerForRecyclerviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepTrackerForRecyclerviewBinding.bind(view)


        val application = requireNotNull(this.activity).application
        val datasource = roomdatabase.getInstance(application).roomDao

        val viewModelFactory = SleepTrackerViewModelFactoryForRecyclerview(datasource, application)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(SleepTrackerViewModelForRecyclerview::class.java)


        binding = FragmentSleepTrackerForRecyclerviewBinding.bind(view)

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

                    SleepTrackerFragmentForRecyclerviewDirections.actionSleepTrackerFragmentForRecyclerviewToSleepQualityFragmentForRecyclerview(it.nightId)
                ) //parameter sleepnigt ke
                viewModel.doneNavigating()
            }

        })



        /** //Button visibility functions
        viewModel.StartButtonVisible.observe(viewLifecycleOwner, Observer {
            binding.startTrackingbtn.isEnabled = true
        })

        viewModel.StopButtonVisible.observe(viewLifecycleOwner, Observer {
            binding.StopTrackingbtn.isEnabled = true
        })

        viewModel.ClearButtonVisible.observe(viewLifecycleOwner, Observer {
            binding.clearbtn.isEnabled = true
        })
**/


    }
}
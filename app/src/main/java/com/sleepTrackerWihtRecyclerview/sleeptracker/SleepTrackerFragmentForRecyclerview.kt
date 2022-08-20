package com.sleepTrackerWihtRecyclerview.sleeptracker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentSleepTrackerForRecyclerviewBinding
import com.sleepTrackerWihtRecyclerview.Room.roomdatabase


class SleepTrackerFragmentForRecyclerview :
    Fragment(R.layout.fragment_sleep_tracker_for_recyclerview) {

//    private lateinit var viewModel: SleepTrackerViewModel
//    private lateinit var viewModelFactory: SleepTrackerViewModelFactory

    private lateinit var binding: FragmentSleepTrackerForRecyclerviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepTrackerForRecyclerviewBinding.bind(view)


        val application = requireNotNull(this.activity).application
        val datasource = roomdatabase.getInstance(application).roomDao

        val viewModelFactory = SleepTrackerViewModelFactoryForRecyclerview(datasource, application)
        val viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(SleepTrackerViewModelForRecyclerview::class.java)


        binding = FragmentSleepTrackerForRecyclerviewBinding.bind(view)

        binding.startButton.setOnClickListener {
            viewModel.onStartTracking()
        }

        binding.stopButton.setOnClickListener {
            viewModel.onStoptracking()
        }

        binding.clearButton.setOnClickListener {
            viewModel.onClear()
        }


        /** viewModel.nightsString.observe(viewLifecycleOwner, Observer {
        //update word
        binding.showTrackina.text = it.toString() }) */

        //binding our recycle view with the adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.sleepListRecyclerview.layoutManager = layoutManager

        val adapter = SleepNightAdapter(SleepNightListener { nightid ->
            /*Toast.makeText(context,"${nightid}",Toast.LENGTH_LONG).show()*/
            /*passing the click item data to viewModel first*/
            viewModel.onSleepNightClicked(nightid)
        })
        binding.sleepListRecyclerview.adapter = adapter

        viewModel.nights.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
            }
        })


        //navigate to sleep quality after we stop tracking
        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentForRecyclerviewDirections.actionSleepTrackerFragmentForRecyclerviewToSleepQualityFragmentForRecyclerview(
                        night.nightId
                    )
                ) //parameter sleepnigt key
                viewModel.doneNavigating()
            }

        })

        viewModel.navigateToSleepDataQuality.observe(viewLifecycleOwner, Observer { night ->

            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentForRecyclerviewDirections
                        .actionSleepTrackerFragmentForRecyclerviewToSleepdetailsFragment(night))
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
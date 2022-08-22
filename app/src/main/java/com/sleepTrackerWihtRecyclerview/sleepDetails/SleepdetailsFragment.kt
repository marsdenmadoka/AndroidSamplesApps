package com.sleepTrackerWihtRecyclerview.sleepDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentSleepdetailsForRecyclerviwerBinding
import com.sleepTrackerWihtRecyclerview.Room.roomdatabase
import com.sleepTrackerWihtRecyclerview.convertDurationToFormatted
import com.sleepTrackerWihtRecyclerview.convertNumericQualityToString

class SleepdetailsFragment : Fragment(R.layout.fragment_sleepdetails_for_recyclerviwer) {

    private lateinit var binding: FragmentSleepdetailsForRecyclerviwerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepdetailsForRecyclerviwerBinding.bind(view)


        val application = requireNotNull(this.activity).application
        val arguments = SleepdetailsFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = roomdatabase.getInstance(application).roomDao
        val viewModelFactory = SleepdetailsViewModelFactory(arguments.sleepNightKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val sleepdetailsViewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SleepdetailsViewModel::class.java)


        //binding.qualityString.text = sleepdetailsViewModel.night.value?.sleepQuality.toString()
        sleepdetailsViewModel.night.observe(viewLifecycleOwner, Observer {

            binding.qualityString.text = "your sleep quality ${ convertNumericQualityToString(it.sleepQuality, context!!.resources)}"

            binding.sleepLength.text = "you slept ${convertDurationToFormatted(it.startTimeMilli, it.endTimeMilli, context!!.resources)}"

            binding.qualityImage.setImageResource(
                when (it.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )

        })

        binding.closebtn.setOnClickListener {
            sleepdetailsViewModel.onClose()
        }

        sleepdetailsViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    SleepdetailsFragmentDirections.actionSleepdetailsFragmentToSleepTrackerFragmentForRecyclerview()
                )
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepdetailsViewModel.doneNavigating()
            }
        })

    }
}

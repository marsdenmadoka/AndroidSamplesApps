package com.sleepTrackerWihtRecyclerview.sleepDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gameudacity.R
import com.example.gameudacity.databinding.FragmentSleepdetailsForRecyclerviwerBinding

class SleepdetailsFragment : Fragment(R.layout.fragment_sleepdetails_for_recyclerviwer)  {

    private lateinit var binding: FragmentSleepdetailsForRecyclerviwerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSleepdetailsForRecyclerviwerBinding.bind(view)

    }
}
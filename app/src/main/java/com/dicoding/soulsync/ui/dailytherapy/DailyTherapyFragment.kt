package com.dicoding.soulsync.ui.dailytherapy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.databinding.FragmentDailyTherapyBinding

class DailyTherapyFragment : Fragment() {

    private lateinit var dailyTherapyViewModel: DailyTherapyViewModel
    private lateinit var adapter: DailyTherapyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDailyTherapyBinding.inflate(inflater, container, false)

        adapter = DailyTherapyAdapter { therapy, isChecked ->
            dailyTherapyViewModel.updateTherapyStatus(therapy, isChecked)
        }

        binding.recyclerViewDailyTherapy.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDailyTherapy.adapter = adapter

        dailyTherapyViewModel = ViewModelProvider(this)[DailyTherapyViewModel::class.java]

        dailyTherapyViewModel.dailyTherapies.observe(viewLifecycleOwner) { dailyTherapies ->
            adapter.submitList(dailyTherapies)
        }

        dailyTherapyViewModel.getDailyTherapies()

        return binding.root
    }
}

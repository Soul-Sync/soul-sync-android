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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DailyTherapyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDailyTherapyBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerViewDailyTherapy
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = DailyTherapyAdapter()
        recyclerView.adapter = adapter

        dailyTherapyViewModel = ViewModelProvider(this)[DailyTherapyViewModel::class.java]

        dailyTherapyViewModel.dailyTherapies.observe(viewLifecycleOwner) { dailyTherapies ->
            adapter.submitList(dailyTherapies)
        }

        dailyTherapyViewModel.getDailyTherapies()

        return binding.root
    }
}


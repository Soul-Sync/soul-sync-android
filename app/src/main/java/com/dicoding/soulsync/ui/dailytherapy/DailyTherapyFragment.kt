package com.dicoding.soulsync.ui.dailytherapy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.soulsync.databinding.FragmentDailyTherapyBinding

class DailyTherapyFragment : Fragment() {

    private var _binding: FragmentDailyTherapyBinding? = null
    private val binding get() = _binding!!

    private lateinit var dailyTherapyViewModel: DailyTherapyViewModel
    private lateinit var adapter: DailyTherapyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyTherapyBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupViewModel()
        observeViewModel()
        setupBackButton() // Tambahkan listener untuk tombol kembali

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = DailyTherapyAdapter { therapy, isChecked ->
            dailyTherapyViewModel.updateTherapyStatus(therapy, isChecked)
        }
        binding.recyclerViewDailyTherapy.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDailyTherapy.adapter = adapter
    }

    private fun setupViewModel() {
        dailyTherapyViewModel = ViewModelProvider(this)[DailyTherapyViewModel::class.java]
    }

    private fun observeViewModel() {
        dailyTherapyViewModel.dailyTherapies.observe(viewLifecycleOwner) { dailyTherapies ->
            adapter.submitList(dailyTherapies)
        }
        dailyTherapyViewModel.getDailyTherapies()
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            // Fungsi untuk kembali ke Fragment atau Activity sebelumnya
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

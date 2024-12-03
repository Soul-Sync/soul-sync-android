package com.dicoding.soulsync.ui.musicrelax

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.soulsync.R
import com.dicoding.soulsync.databinding.FragmentMusicRelaxBinding

class MusicRelaxFragment : Fragment() {

    private var _binding: FragmentMusicRelaxBinding? = null
    private val binding get() = _binding!!

    private val musicRelaxViewModel: MusicRelaxViewModel by viewModels()
    private lateinit var adapter: MusicRelaxAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicRelaxBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeMusicList()
        setupBackButton() // Tambahkan listener untuk tombol kembali

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = MusicRelaxAdapter(emptyList(), emptyList())
        binding.recyclerViewMusicRelax.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMusicRelax.adapter = adapter
    }

    private fun observeMusicList() {
        musicRelaxViewModel.musicList.observe(viewLifecycleOwner) { musicList ->
            val musicImages = musicList.map { R.drawable.contoh_gambar_lagu }
            adapter.updateData(musicList, musicImages)
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            // Navigasi ke halaman sebelumnya
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

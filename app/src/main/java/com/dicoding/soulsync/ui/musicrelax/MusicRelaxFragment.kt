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

    private val musicRelaxViewModel: MusicRelaxViewModel by viewModels() // Menggunakan delegate viewModels() untuk lebih bersih
    private lateinit var adapter: MusicRelaxAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicRelaxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi Adapter
        adapter = MusicRelaxAdapter(emptyList(), emptyList()) // Awalnya kosong
        binding.recyclerViewMusicRelax.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMusicRelax.adapter = adapter

        // Observasi LiveData dari ViewModel
        musicRelaxViewModel.musicList.observe(viewLifecycleOwner) { musicList ->
            val musicImages = musicList.map { R.drawable.contoh_gambar_lagu } // Sesuaikan dengan gambar untuk lagu
            adapter.updateData(musicList, musicImages) // Perbarui data di adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



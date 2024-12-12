package com.dicoding.soulsync.ui.musicrelax

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.model.Music

class MusicRelaxFragment : Fragment() {

    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music_relax, container, false)
        val musicRecyclerView: RecyclerView = view.findViewById(R.id.musicRecyclerView)

        val btnBack: ImageView = view.findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            // Kembali ke fragment sebelumnya
            parentFragmentManager.popBackStack()
        }
        // Sample data
        val musicList = listOf(

            Music("Leaving","AERØHEAD", R.raw.leaving),
            Music("Me Time","Avanti", R.raw.me_time),
            Music("Universe","Sappheiros", R.raw.universe),
            Music("Gravity","Extenz", R.raw.gravity),
            Music("Awake","Nomyn", R.raw.awake),
            Music("Etheral","Punch Deck", R.raw.ethereal),
            Music("Blue","Yung Kai", R.raw.blue),
            Music("Love","Wave To Earth", R.raw.love),
            Music("K","Cigarettes After Sex", R.raw.k),
            Music("Apocalypse","Cigarettes After Sex", R.raw.apocalypse)


        )

        // Setup RecyclerView
        musicAdapter = MusicAdapter(musicList)
        musicRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        musicRecyclerView.adapter = musicAdapter

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        musicAdapter.releaseMediaPlayer()
    }
}

package com.dicoding.soulsync.ui.musicrelax

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicRelaxViewModel : ViewModel() {

    // LiveData untuk daftar musik
    private val _musicList = MutableLiveData<List<String>>().apply {
        value = listOf(
            "Kini Mereka Tahu - Bernadya",
            "Satu Bulan - Bernadya",
            "Untungnya Hidup Harus Berjalan - Bernadya",
            "Waktu Yang Salah - Fiersa Bersari",
            "Yank - Wali",
            "Zona Nyaman - Fourtwnty",
            "Lathi - Weird Genius",
            "To The Bone - Pamungkas",
            "Kasih Putih - Glenn Fredly",
            "Manusia Kuat - Tulus",
            "Hati-Hati di Jalan - Tulus",
            "Fix You - Coldplay",
            "Someone Like You - Adele",
            "Easy On Me - Adele",
            "Viva La Vida - Coldplay",
            "Shape of You - Ed Sheeran",
            "Photograph - Ed Sheeran",
            "Perfect - Ed Sheeran",
            "All of Me - John Legend",
            "A Thousand Years - Christina Perri",
            "Rolling in the Deep - Adele",
            "Bohemian Rhapsody - Queen",
            "Imagine - John Lennon",
            "Let It Be - The Beatles",
            "Love of My Life - Queen"
        )
    }
    val musicList: LiveData<List<String>> = _musicList

}

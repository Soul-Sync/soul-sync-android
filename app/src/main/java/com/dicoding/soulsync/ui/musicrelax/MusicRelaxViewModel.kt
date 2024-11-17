package com.dicoding.soulsync.ui.musicrelax

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicRelaxViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Music Relax Fragment"
    }
    val text: LiveData<String> = _text
}
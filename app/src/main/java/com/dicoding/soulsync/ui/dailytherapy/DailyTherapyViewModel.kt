package com.dicoding.soulsync.ui.dailytherapy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyTherapyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Daily Therapy Fragment"
    }
    val text: LiveData<String> = _text
}
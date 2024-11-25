package com.dicoding.soulsync.ui.dailytherapy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.soulsync.model.DailyTherapy
import com.dicoding.soulsync.repository.DailyTherapyRepository
import kotlinx.coroutines.launch

class DailyTherapyViewModel(application: Application) : AndroidViewModel(application) {

    private val dailyTherapyRepository: DailyTherapyRepository = DailyTherapyRepository()
    private val _dailyTherapies = MutableLiveData<List<DailyTherapy>>()
    val dailyTherapies: LiveData<List<DailyTherapy>> get() = _dailyTherapies

    fun getDailyTherapies() {
        viewModelScope.launch {
            val therapies = dailyTherapyRepository.getDailyTherapies()
            _dailyTherapies.postValue(therapies)
        }
    }

    fun updateTherapyStatus(
        therapy: DailyTherapy,
        isChecked: Boolean
    ) {
        viewModelScope.launch {
            dailyTherapyRepository.updateTherapyStatus(therapy, isChecked)
            _dailyTherapies.postValue(_dailyTherapies.value)
        }
    }
}



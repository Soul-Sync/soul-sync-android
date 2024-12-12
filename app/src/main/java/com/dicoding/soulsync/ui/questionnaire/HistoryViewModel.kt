package com.dicoding.soulsync.ui.questionnaire

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.soulsync.model.QuestionnaireHistory
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    private val _history = MutableLiveData<List<QuestionnaireHistory>>()
    val history: LiveData<List<QuestionnaireHistory>> get() = _history

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchHistory() {
        _isLoading.value = true
        Log.d("HistoryViewModel", "Fetching history started")
        viewModelScope.launch {
            val result = repository.getHistory()
            result.onSuccess {
                Log.d("HistoryViewModel", "History fetched successfully: ${it.payload.size} items")
                _history.value = it.payload
                _errorMessage.value = null
            }.onFailure {
                Log.e("HistoryViewModel", "Error fetching history: ${it.message}")
                _errorMessage.value = it.message
            }
            _isLoading.value = false
        }
    }

}
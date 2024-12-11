package com.dicoding.soulsync.ui.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.api.ApiService
import com.dicoding.soulsync.model.AnswerRequest
import com.dicoding.soulsync.model.QuestionResponse
import com.dicoding.soulsync.model.QuestionnaireResponse
import kotlinx.coroutines.launch

class QuestionViewModel(private val token: String) : ViewModel() {

    private val _questions = MutableLiveData<QuestionResponse>()
    val questions: LiveData<QuestionResponse> = _questions

    private val _submissionResult = MutableLiveData<QuestionnaireResponse>()
    val submissionResult: LiveData<QuestionnaireResponse> = _submissionResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val apiService: ApiService by lazy {
        ApiClient(token).createService()
    }

    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val response = apiService.getQuestions()
                if (response.isSuccessful) {
                    _questions.postValue(response.body())
                    Log.d("QuestionViewModel", "Questions fetched successfully")
                } else {
                    val errorMessage = "Error: ${response.message()} (${response.code()})"
                    _error.postValue(errorMessage)
                    Log.e("QuestionViewModel", errorMessage)
                }
            } catch (e: Exception) {
                val exceptionMessage = "Exception: ${e.message}"
                _error.postValue(exceptionMessage)
                Log.e("QuestionViewModel", exceptionMessage, e)
            }
        }
    }

    fun submitAnswers(answers: Map<String, Int>) {
        viewModelScope.launch {
            try {
                // Buat objek request
                val request = AnswerRequest(answers)

                // Log data yang akan dikirim ke server
                Log.d("QuestionViewModel", "Submitting answers: $request")

                // Kirim request ke server
                val response = apiService.submitQuestionnaire(request)

                // Periksa respons
                if (response.isSuccessful) {
                    _submissionResult.postValue(response.body())
                    Log.d("QuestionViewModel", "Answers submitted successfully")
                } else {
                    val errorMessage = "Error: ${response.message()} (${response.code()})"
                    _error.postValue(errorMessage)
                    Log.e("QuestionViewModel", errorMessage)
                }
            } catch (e: Exception) {
                val exceptionMessage = "Exception: ${e.message}"
                _error.postValue(exceptionMessage)
                Log.e("QuestionViewModel", exceptionMessage, e)
            }
        }
    }

}

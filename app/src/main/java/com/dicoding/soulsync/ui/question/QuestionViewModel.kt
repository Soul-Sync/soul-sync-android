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

                // Log data request sebelum dikirim
                Log.d("QuestionViewModel", "Request Data: $request")

                // Kirim permintaan ke server
                val response = apiService.submitQuestionnaire(request)

                // Periksa respons
                if (response.isSuccessful) {
                    // Log data yang diterima dari server
                    Log.d("QuestionViewModel", "Server Response: ${response.body()}")
                    _submissionResult.postValue(response.body())
                    Log.d("QuestionViewModel", "Answers submitted successfully")
                } else {
                    // Log error message dan error body
                    val errorBody = response.errorBody()?.string()
                    Log.e("QuestionViewModel", "Error: ${response.message()} (${response.code()})")
                    Log.e("QuestionViewModel", "Server Error Body: $errorBody")
                    _error.postValue("Error: ${response.message()} (${response.code()})")
                }
            } catch (e: Exception) {
                // Log exception
                val exceptionMessage = "Exception: ${e.message}"
                _error.postValue(exceptionMessage)
                Log.e("QuestionViewModel", exceptionMessage, e)
            }
        }
    }



    fun fetchQuestionnaireById(id: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getQuestionnaireById(id)
                if (response.isSuccessful) {
                    _submissionResult.postValue(response.body())
                    Log.d("QuestionViewModel", "Questionnaire fetched successfully")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Error: ${response.message()} (${response.code()})"
                    _error.postValue(errorMessage)
                    Log.e("QuestionViewModel", "Error Body: $errorBody")
                }
            } catch (e: Exception) {
                val exceptionMessage = "Exception: ${e.message}"
                _error.postValue(exceptionMessage)
                Log.e("QuestionViewModel", exceptionMessage, e)
            }
        }
    }



}

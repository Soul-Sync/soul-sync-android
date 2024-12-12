package com.dicoding.soulsync.ui.questionnaire

import android.util.Log
import com.dicoding.soulsync.api.ApiService
import com.dicoding.soulsync.model.HistoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepository(private val apiService: ApiService) {
    suspend fun getHistory(): Result<HistoryResponse> {
        Log.d("HistoryRepository", "Requesting history from API")
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getQuestionnaireHistory()
                if (response.isSuccessful) {
                    Log.d("HistoryRepository", "API response successful")
                    response.body()?.let {
                        Log.d("HistoryRepository", "Response body contains ${it.payload.size} items")
                        Result.success(it)
                    } ?: Result.failure(Throwable("Response body is null"))
                } else {
                    Log.e("HistoryRepository", "API response failed: ${response.message()}")
                    Result.failure(Throwable(response.message()))
                }
            } catch (e: Exception) {
                Log.e("HistoryRepository", "Exception occurred: ${e.message}")
                Result.failure(e)
            }
        }
    }
}

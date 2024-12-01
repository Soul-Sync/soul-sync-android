package com.dicoding.soulsync.ui.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.soulsync.api.ArticleApiService
import com.dicoding.soulsync.api.RetrofitClient
import com.dicoding.soulsync.model.Article
import com.dicoding.soulsync.model.ArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val apiService = RetrofitClient.instance.create(ArticleApiService::class.java)

    fun fetchArticles() {
        apiService.getArticles().enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    val articlesData = response.body()?.news_results ?: emptyList()
                    Log.d("ArticleViewModel", "Articles received: $articlesData") // Log data artikel
                    _articles.value = articlesData
                } else {
                    Log.e("ArticleViewModel", "Response not successful: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e("ArticleViewModel", "Error fetching articles", t) // Log error jika request gagal
            }
        })
    }
}

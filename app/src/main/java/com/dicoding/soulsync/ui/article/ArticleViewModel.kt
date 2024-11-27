package com.dicoding.soulsync.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.soulsync.api.ApiConfig
import com.dicoding.soulsync.model.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    fun fetchArticles() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getAllArticles()
//        client.enqueue(object : Callback<List<Article>> {
//            override fun onResponse(
//                call: Call<List<Article>>,
//                response: Response<List<Article>>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _articles.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
//                _isLoading.value = false
//            }
//        })
//    }
}

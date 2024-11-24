package com.dicoding.soulsync.api

import com.dicoding.soulsync.model.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApiService {
    @GET("article/")
    fun getAllArticles(): Call<List<Article>>

    @GET("article/{id}")
    fun getArticleDetail(@Path("id") articleId: String): Call<Article>
}

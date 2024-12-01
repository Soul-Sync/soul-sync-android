package com.dicoding.soulsync.api

import com.dicoding.soulsync.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("search.json")
    fun getArticles(
        @Query("engine") engine: String = "google_news",
        @Query("q") query: String = "mental+health",
        @Query("hl") language: String = "id",
        @Query("api_key") apiKey: String = "3350614cece257bf1be6e54a1e6cde5498ec157af2c1e3921cc40b3e4433c230"
    ): Call<ArticleResponse>
}




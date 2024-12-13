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
        @Query("api_key") apiKey: String = "db8f07c29686e3727b23a5553884c97a9b26eacf74ed8a1af3fdaa41a3acdad9"
    ): Call<ArticleResponse>
}




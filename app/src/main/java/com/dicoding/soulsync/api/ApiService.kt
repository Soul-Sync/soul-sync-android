package com.dicoding.soulsync.api

import com.dicoding.soulsync.model.Article
import com.dicoding.soulsync.model.UserRequest
import com.dicoding.soulsync.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("article/")
    fun getAllArticles(): Call<List<Article>>

    @GET("article/{id}")
    fun getArticleDetail(@Path("id") articleId: String): Call<Article>

    @POST("auth/register")
    fun register(@Body request: UserRequest): Call<UserResponse>

    @POST("auth/login")
    fun login(@Body request: UserRequest): Call<UserResponse>
}

package com.dicoding.soulsync.api

import com.dicoding.soulsync.model.LoginResponse
import com.dicoding.soulsync.model.ProfileResponse
import com.dicoding.soulsync.model.QuestionResponse
import com.dicoding.soulsync.model.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest: Map<String, String>
    ): LoginResponse

    @POST("/auth/register")
    suspend fun register(
        @Body registerRequest: Map<String, String>
    ): RegisterResponse


    @GET("/profile")
    suspend fun getProfile(): ProfileResponse

    @POST("/profile")
    suspend fun updateProfile(
        @Body profileRequest: Map<String, String>
    ): ProfileResponse

    @GET("/question")
    suspend fun getQuestions(): Response<QuestionResponse>


}

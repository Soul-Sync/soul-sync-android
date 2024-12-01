package com.dicoding.soulsync.api

import com.dicoding.soulsync.model.LoginRequest
import com.dicoding.soulsync.model.LoginResponse
import com.dicoding.soulsync.model.RegisterRequest
import com.dicoding.soulsync.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>


}

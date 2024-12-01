package com.dicoding.soulsync.api

import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://dicoding-442102.et.r.appspot.com/"

    // Tambahkan interceptor untuk menambahkan Bearer Token
    private fun provideAuthInterceptor(preference: UserPreference): Interceptor {
        return Interceptor { chain ->
            val token = runBlocking { // Ambil token secara sinkron
                preference.getSession().first().token
            }
            val request = chain.request().newBuilder()
            if (!token.isNullOrEmpty()) {
                request.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(request.build())
        }
    }

    // Logging interceptor untuk debugging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Konfigurasi OkHttpClient
    private fun provideClient(preference: UserPreference): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideAuthInterceptor(preference)) // Tambahkan auth interceptor
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Buat instance Retrofit
    fun create(preference: UserPreference): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideClient(preference))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

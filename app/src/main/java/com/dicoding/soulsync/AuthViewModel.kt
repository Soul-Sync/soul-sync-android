package com.dicoding.soulsync

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.model.LoginRequest
import com.dicoding.soulsync.model.LoginResponse
import com.dicoding.soulsync.model.RegisterRequest
import com.dicoding.soulsync.model.RegisterResponse
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> get() = _loginResult

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> get() = _registerResult

    fun login(email: String, password: String) {
        val request = LoginRequest(email, password)
        ApiClient.create(userPreference).login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        _loginResult.postValue(it)

                        // Simpan token ke DataStore
                        viewModelScope.launch {
                            userPreference.saveSession(it)
                        }
                    }
                } else {
                    _loginResult.postValue(LoginResponse("error", "Login failed"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.postValue(LoginResponse("error", t.message ?: "Unknown error"))
            }
        })
    }

    fun register(name: String, email: String, password: String) {
        val request = RegisterRequest(name, email, password)
        ApiClient.create(userPreference).register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _registerResult.postValue(response.body())
                } else {
                    _registerResult.postValue(RegisterResponse("error", "Register failed"))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _registerResult.postValue(RegisterResponse("error", t.message ?: "Unknown error"))
            }
        })
    }
}

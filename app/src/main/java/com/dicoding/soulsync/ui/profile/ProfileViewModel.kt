package com.dicoding.soulsync.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _dateOfBirth = MutableLiveData<String>()
    val dateOfBirth: LiveData<String> get() = _dateOfBirth

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> get() = _gender

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // Simulasi data awal profil
    init {
        loadInitialProfile()
    }

    private fun loadInitialProfile() {
        _loading.value = true
        // Simulasikan pengambilan data dari API atau database
        _name.value = "John Doe"
        _dateOfBirth.value = "1990-01-01"
        _gender.value = "Male"
        _email.value = "johndoe@example.com"
        _loading.value = false
    }

    fun setProfile(name: String, dateOfBirth: String, gender: String, email: String) {
        _name.value = name
        _dateOfBirth.value = dateOfBirth
        _gender.value = gender
        _email.value = email
    }

    fun saveProfile(name: String, dateOfBirth: String, gender: String, email: String) {
        _loading.value = true
        // Simulasikan penyimpanan data
        _name.value = name
        _dateOfBirth.value = dateOfBirth
        _gender.value = gender
        _email.value = email
        _loading.value = false
    }

    fun logout() {
        // Tambahkan logika logout di sini
    }
}

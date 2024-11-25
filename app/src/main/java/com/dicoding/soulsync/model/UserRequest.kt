package com.dicoding.soulsync.model

data class UserRequest(
    val name: String? = null, // Opsional untuk register
    val email: String,
    val password: String
)
package com.dicoding.soulsync.model

data class UserResponse(
    val message: String,
    val token: String? = null // Token hanya untuk login
)
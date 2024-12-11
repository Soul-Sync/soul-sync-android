package com.dicoding.soulsync.model


data class LoginResponse(
    val status: String,
    val message: String,
    val payload: Payload
)

data class Payload(
    val token: String,
    val token_type: String,
    val user: User
)

data class User(
    val id: String,
    val email: String,
    val name: String,
    val date_of_birth: String?,
    val gender: String?,
    val avatar: String?,
    val createdAt: String,
    val updatedAt: String
)

data class RegisterResponse(
    val status: String,
    val message: String,
    val payload: Payload
)


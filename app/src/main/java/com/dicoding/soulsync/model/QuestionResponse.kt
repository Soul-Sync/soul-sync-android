package com.dicoding.soulsync.model

data class QuestionResponse(
    val status: String,
    val message: String,
    val payload: Payload
)


data class Question(
    val id: String,
    val question: String,
    val options: Map<String, String>, // Key-value untuk opsi jawaban
    val sort: Int,
    val createdAt: String,
    val updatedAt: String
)

package com.dicoding.soulsync.model

data class QuestionResponse(
    val status: String,
    val message: String,
    val payload: QuestionPayload
)

data class QuestionPayload(
    val question: List<QuestionItem>
)

data class QuestionItem(
    val id: String,
    val question: String,
    val options: Map<String, String>?,
    val sort: Int,
    val keyword: String
)

data class AnswerRequest(
    val answer: Map<String, Int> // Menggunakan Map untuk menyimpan jawaban
)
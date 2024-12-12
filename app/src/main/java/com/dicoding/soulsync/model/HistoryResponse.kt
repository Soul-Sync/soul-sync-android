package com.dicoding.soulsync.model

data class HistoryResponse(
    val status: String,
    val message: String,
    val payload: List<QuestionnaireHistory>
)

data class QuestionnaireHistory(
    val id: String,
    val user_id: String,
    val date: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)
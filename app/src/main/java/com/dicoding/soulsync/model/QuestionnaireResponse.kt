package com.dicoding.soulsync.model

data class QuestionnaireResponse(
    val status: String,
    val message: String,
    val payload: QuestionnairePayload?
)

data class QuestionnairePayload(
    val id: String,
    val user_id: String,
    val date: String,
    val status: String
)

package com.dicoding.soulsync.model

data class QuestionnaireListResponse(
    val status: String,
    val message: String,
    val data: List<QuestionnaireData>
)

data class QuestionnaireData(
    val id: String,
    val userId: String,
    val createdDate: String,
    val currentStatus: String,
    val createdAt: String,
    val updatedAt: String
)

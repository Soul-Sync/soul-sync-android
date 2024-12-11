package com.dicoding.soulsync.model

data class QuestionnaireResponse(
    val status: String,
    val message: String,
    val payload: QuestionnairePayload?
)

data class QuestionnairePayload(
    val questionnaire: QuestionnaireDetail
)

data class QuestionnaireDetail(
    val id: String,
    val user_id: String,
    val date: String,
    val status: String,
    val music_recommendation: List<MusicRecommendation>,
    val theraphy_recommendation: List<TherapyRecommendation>
)

data class MusicRecommendation(
    val id: String,
    val title: String,
    val artist: String,
    val thumbnail: String,
    val link: String
)

data class TherapyRecommendation(
    val id: String,
    val name: String
)


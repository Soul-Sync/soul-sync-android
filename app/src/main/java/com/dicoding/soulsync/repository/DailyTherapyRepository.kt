package com.dicoding.soulsync.repository

import com.dicoding.soulsync.model.DailyTherapy

class DailyTherapyRepository {

    fun getDailyTherapies(): List<DailyTherapy> {
        return listOf(
            DailyTherapy("Mendengarkan Musik Relax", false),
            DailyTherapy("Tidur 30 Menit", false),
            DailyTherapy("Meditasi", false)
        )
    }

    fun updateTherapyStatus(
        therapy: DailyTherapy,
        isChecked: Boolean
    ) {
        therapy.isCompleted = isChecked
    }
}

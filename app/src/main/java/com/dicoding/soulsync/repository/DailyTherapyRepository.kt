package com.dicoding.soulsync.repository

import android.content.Context
import android.content.SharedPreferences
import com.dicoding.soulsync.model.DailyTherapy
import java.text.SimpleDateFormat
import java.util.*

class DailyTherapyRepository(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("daily_therapy_prefs", Context.MODE_PRIVATE)

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Fungsi untuk mendapatkan daftar terapi harian
    fun getDailyTherapies(): List<DailyTherapy> {
        resetIfNewDay()
        return listOf(
            DailyTherapy("Mendengarkan Musik Relax", isChecked("Mendengarkan Musik Relax")),
            DailyTherapy("Tidur 30 Menit", isChecked("Tidur 30 Menit")),
            DailyTherapy("Meditasi", isChecked("Meditasi")),
            DailyTherapy("Olahraga ringan", isChecked("Olahraga ringan")),
            DailyTherapy("Minum yg cukup", isChecked("Minum yg cukup"))
        )
    }

    // Fungsi untuk memperbarui status checklist
    fun updateTherapyStatus(therapy: DailyTherapy, isChecked: Boolean) {
        sharedPreferences.edit().putBoolean(therapy.title, isChecked).apply()
    }

    // Mengecek apakah terapi sudah tercentang
    private fun isChecked(title: String): Boolean {
        return sharedPreferences.getBoolean(title, false)
    }

    // Reset data jika hari baru
    private fun resetIfNewDay() {
        val today = dateFormat.format(Date())
        val lastSavedDate = sharedPreferences.getString("last_date", "")

        if (lastSavedDate != today) {
            sharedPreferences.edit().clear().apply()
            sharedPreferences.edit().putString("last_date", today).apply()
        }
    }
}

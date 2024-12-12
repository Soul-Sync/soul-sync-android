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
            DailyTherapy("Melakukan olahraga ringan seperti jalan kaki selama 15 menit", isChecked("Melakukan olahraga ringan seperti jalan kaki selama 15 menit")),
            DailyTherapy("Berbicara dengan teman atau keluarga yang suportif", isChecked("Berbicara dengan teman atau keluarga yang suportif")),
            DailyTherapy("Melakukan meditasi mindfulness selama 5-10 menit", isChecked("Melakukan meditasi mindfulness selama 5-10 menit")),
            DailyTherapy("Menghabiskan waktu di alam, seperti taman atau pantai", isChecked("Menghabiskan waktu di alam, seperti taman atau pantai")),
            DailyTherapy("Membaca buku atau puisi inspiratif", isChecked("Membaca buku atau puisi inspiratif")),
            DailyTherapy("Menonton video lucu untuk meredakan stres", isChecked("Menonton video lucu untuk meredakan stres")),
            DailyTherapy("Mendengarkan musik yang menenangkan selama 10 menit", isChecked("Mendengarkan musik yang menenangkan selama 10 menit")),
            DailyTherapy("Berlatih teknik pernapasan dalam selama 3-5 menit", isChecked("Berlatih teknik pernapasan dalam selama 3-5 menit")),
            DailyTherapy("Menulis jurnal untuk mencurahkan perasaan", isChecked("Menulis jurnal untuk mencurahkan perasaan")),
            DailyTherapy("Menggambar atau mewarnai sebagai bentuk terapi seni", isChecked("Menggambar atau mewarnai sebagai bentuk terapi seni"))

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

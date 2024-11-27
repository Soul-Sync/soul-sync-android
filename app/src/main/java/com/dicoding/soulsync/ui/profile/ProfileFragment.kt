package com.dicoding.soulsync.ui.profile


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.soulsync.R
import java.util.Calendar

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referensi ke EditText Tanggal Lahir
        val etBirthDate = view.findViewById<EditText>(R.id.etBirthDate)

        // Membuka DatePickerDialog saat EditText diklik
        etBirthDate.setOnClickListener {
            showDatePicker(etBirthDate)
        }

        // Tombol Edit
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)
        btnEdit.setOnClickListener {
            Toast.makeText(requireContext(), "Data Profil Diperbarui", Toast.LENGTH_SHORT).show()
        }

        // Tombol Logout
        val btnLogout = view.findViewById<Button>(R.id.logoutButton)
        btnLogout.setOnClickListener {
            // Tambahkan logika logout sesuai kebutuhan
            Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk menampilkan DatePickerDialog
    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            editText.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }
}

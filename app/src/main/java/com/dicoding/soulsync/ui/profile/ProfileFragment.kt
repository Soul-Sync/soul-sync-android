package com.dicoding.soulsync.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.soulsync.R
import com.dicoding.soulsync.ui.login.LoginActivity
import com.dicoding.soulsync.utils.SessionManager
import java.util.Calendar
import android.app.DatePickerDialog

class ProfileFragment : Fragment() {
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sessionManager = SessionManager(requireContext())

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
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Clear session
            sessionManager.clearSession()

            // Logout dan kembali ke halaman login
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // Menampilkan toast untuk memberi tahu bahwa logout berhasil
            Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show()
        }

        return view
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

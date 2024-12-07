package com.dicoding.soulsync.ui.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.databinding.ActivityEditProfileBinding
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userPreference: UserPreference
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Formatter untuk hasil DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        // Fetch initial profile data
        fetchProfileData()

        // Handle click on date input to show DatePickerDialog
        binding.dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Save changes on button click
        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val dateOfBirth = binding.dateEditText.text.toString()

            // Get selected gender from RadioGroup
            val selectedGenderId = binding.genderRadioGroup.checkedRadioButtonId
            val selectedGender = if (selectedGenderId != -1) {
                val radioButton = findViewById<RadioButton>(selectedGenderId)
                radioButton.text.toString() // Result: "Laki-laki" or "Perempuan"
            } else {
                ""
            }

            if (validateInput(name, dateOfBirth, selectedGender)) {
                updateProfile(name, dateOfBirth, selectedGender)
            }
        }
    }

    private fun fetchProfileData() {
        lifecycleScope.launch {
            try {
                val token = userPreference.token.firstOrNull()
                if (token == null) {
                    Toast.makeText(this@EditProfileActivity, "Token not found, please login again.", Toast.LENGTH_SHORT).show()
                    finish()
                    return@launch
                }

                val apiClient = ApiClient(token).createService()
                val response = apiClient.getProfile()

                if (response.status == "success") {
                    val user = response.payload.user
                    binding.nameEditText.setText(user.name)
                    binding.dateEditText.setText(user.date_of_birth)

                    // Set gender based on user data
                    if (user.gender.equals("Laki-laki", true)) {
                        binding.rbMale.isChecked = true
                    } else if (user.gender.equals("Perempuan", true)) {
                        binding.rbFemale.isChecked = true
                    }
                } else {
                    Toast.makeText(this@EditProfileActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProfileActivity, "Failed to load profile data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Tampilkan DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format hasil DatePicker ke yyyy-MM-dd
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val formattedDate = dateFormatter.format(selectedDate.time)
                binding.dateEditText.setText(formattedDate) // Set hasil ke EditText
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun updateProfile(name: String, dateOfBirth: String, gender: String) {
        lifecycleScope.launch {
            try {
                val token = userPreference.token.firstOrNull()
                if (token == null) {
                    Toast.makeText(this@EditProfileActivity, "Token not found, please login again.", Toast.LENGTH_SHORT).show()
                    finish()
                    return@launch
                }

                val apiClient = ApiClient(token).createService()
                val requestBody = mapOf(
                    "name" to name,
                    "date_of_birth" to dateOfBirth,
                    "gender" to gender // "Laki-laki" or "Perempuan"
                )

                Log.d("EditProfileActivity", "Request Body: $requestBody")

                val response = apiClient.updateProfile(requestBody)
                if (response.status == "success") {
                    Toast.makeText(this@EditProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()

                    // Set the result to indicate success and finish activity
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@EditProfileActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("EditProfileActivity", "Error: ${e.message}")
                Toast.makeText(this@EditProfileActivity, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(name: String, dateOfBirth: String, gender: String): Boolean {
        if (name.isBlank()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!dateOfBirth.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
            Toast.makeText(this, "Date of Birth must be in yyyy-MM-dd format", Toast.LENGTH_SHORT).show()
            return false
        }
        if (gender.isBlank() || (gender != "Laki-laki" && gender != "Perempuan")) {
            Toast.makeText(this, "Please select a valid gender", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

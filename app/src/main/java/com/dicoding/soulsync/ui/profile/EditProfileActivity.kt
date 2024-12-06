package com.dicoding.soulsync.ui.profile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.databinding.ActivityEditProfileBinding
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        // Fetch initial profile data
        fetchProfileData()

        // Save changes on button click
        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val dateOfBirth = binding.dateEditText.text.toString()
            val gender = binding.genderEditText.text.toString()

            if (validateInput(name, dateOfBirth, gender)) {
                updateProfile(name, dateOfBirth, gender)
            }
        }
    }

    private fun fetchProfileData() {
        lifecycleScope.launch {
            try {
                val token = userPreference.token.firstOrNull()
                val apiClient = ApiClient(token).createService()
                val response = apiClient.getProfile()

                if (response.status == "success") {
                    val user = response.payload.user
                    binding.nameEditText.setText(user.name)
                    binding.dateEditText.setText(user.date_of_birth)
                    binding.genderEditText.setText(user.gender)
                } else {
                    Toast.makeText(this@EditProfileActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditProfileActivity, "Failed to load profile data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateProfile(name: String, dateOfBirth: String, gender: String) {
        lifecycleScope.launch {
            try {
                val token = userPreference.token.firstOrNull()
                val apiClient = ApiClient(token).createService()
                val requestBody = mapOf(
                    "name" to name,
                    "date_of_birth" to dateOfBirth,
                    "gender" to gender
                )

                Log.d("EditProfileActivity", "Request Body: $requestBody")

                val response = apiClient.updateProfile(requestBody)
                if (response.status == "success") {
                    Toast.makeText(this@EditProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
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
        if (!gender.equals("male", true) && !gender.equals("female", true) &&
            !gender.equals("Laki-laki", true) && !gender.equals("Perempuan", true)) {
            Toast.makeText(this, "Gender must be 'male', 'female', 'Laki-laki', or 'Perempuan'", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

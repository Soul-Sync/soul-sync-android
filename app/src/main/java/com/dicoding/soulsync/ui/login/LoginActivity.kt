package com.dicoding.soulsync.ui.login

import com.dicoding.soulsync.MainActivity
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.databinding.ActivityLoginBinding
import com.dicoding.soulsync.model.LoginResponse
import com.dicoding.soulsync.utils.UserPreference
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.ui.signup.SignupActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        // Handle Register Text Click
        binding.registerTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }


    private fun login(email: String, password: String) {
        val apiClient = ApiClient(null).createService()
        val requestBody = mapOf("email" to email, "password" to password)

        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val response: LoginResponse = apiClient.login(requestBody)

                if (response.status == "success") {
                    val token = response.payload.token
                    saveTokenAndProceed(token)
                } else {
                    Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private suspend fun saveTokenAndProceed(token: String) {
        userPreference.saveToken(token)
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java)) // Redirect to MainActivity
        finish()
    }
}

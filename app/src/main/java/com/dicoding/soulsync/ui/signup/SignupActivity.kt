package com.dicoding.soulsync.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                register(name, email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        val apiClient = ApiClient(null).createService()
        val requestBody = mapOf("name" to name, "email" to email, "password" to password)

        lifecycleScope.launch {
            try {
                // Tampilkan ProgressBar
                binding.progressBar.visibility = View.VISIBLE

                val response = apiClient.register(requestBody)
                if (response.status == "success") {
                    Toast.makeText(this@SignupActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke halaman Login
                } else {
                    Toast.makeText(this@SignupActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SignupActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                // Sembunyikan ProgressBar
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}

package com.dicoding.soulsync.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsync.AuthViewModel
import com.dicoding.soulsync.databinding.ActivitySignupBinding
import com.dicoding.soulsync.ui.ViewModelFactory
import com.dicoding.soulsync.ui.login.LoginActivity
import com.dicoding.soulsync.utils.UserPreference

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    // Menggunakan ViewModelFactory untuk menyediakan dependensi
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                authViewModel.register(name, email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        authViewModel.registerResult.observe(this) { response ->
            showLoading(false)
            if (response.status == "success") {
                Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

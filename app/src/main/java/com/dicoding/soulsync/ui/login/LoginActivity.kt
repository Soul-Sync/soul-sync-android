package com.dicoding.soulsync.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.AuthViewModel
import com.dicoding.soulsync.MainActivity
import com.dicoding.soulsync.databinding.ActivityLoginBinding
import com.dicoding.soulsync.ui.ViewModelFactory
import com.dicoding.soulsync.ui.signup.SignupActivity
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    // Inisialisasi ViewModel dengan ViewModelFactory
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                authViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        authViewModel.loginResult.observe(this) { response ->
            showLoading(false)
            if (response.status == "success") {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                // Simpan sesi login ke DataStore
                lifecycleScope.launch {
                    val userPreference = UserPreference.getInstance(applicationContext)
                    userPreference.saveSession(response)
                }

                // Pindah ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
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

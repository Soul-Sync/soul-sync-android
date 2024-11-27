package com.dicoding.soulsync.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsync.MainActivity
import com.dicoding.soulsync.R
import com.dicoding.soulsync.api.ApiConfig
import com.dicoding.soulsync.databinding.ActivityLoginBinding
import com.dicoding.soulsync.model.UserRequest
import com.dicoding.soulsync.model.UserResponse
import com.dicoding.soulsync.ui.signup.SignupActivity
import com.dicoding.soulsync.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val loginButton: Button = findViewById(R.id.loginButton)
        val emailEditText = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.passwordEditText)
        val registerTextView: TextView = findViewById(R.id.registerTextView)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apiService = ApiConfig.getApiService(this)
            val loginRequest = UserRequest(email = email, password = password)

            apiService.login(loginRequest).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val token = response.body()?.token ?: ""
                        sessionManager.saveLoginSession(token)

                        Toast.makeText(this@LoginActivity, "Login berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login gagal, periksa kembali email/password", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        registerTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}


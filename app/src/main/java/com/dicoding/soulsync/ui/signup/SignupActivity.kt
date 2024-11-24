package com.dicoding.soulsync.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsync.R

class SignupActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signupButton = findViewById(R.id.signupButton)

        // Handle signup button click
        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                // Show error message
                nameEditText.error = if (name.isEmpty()) "Name is required" else null
                emailEditText.error = if (email.isEmpty()) "Email is required" else null
                passwordEditText.error = if (password.isEmpty()) "Password is required" else null
            } else {
                // Proceed with signup (you can add API logic here)
                // Example: Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.dicoding.soulsync.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.soulsync.MainActivity
import com.dicoding.soulsync.R
import com.dicoding.soulsync.start.StartActivity
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val userPreference = UserPreference(applicationContext)

        lifecycleScope.launch {
            delay(3000) // Menunggu animasi splash selesai

            // Ambil token dari UserPreference
            val token = userPreference.token.firstOrNull()

            if (!token.isNullOrEmpty()) {
                // Log untuk memastikan token tersedia
                Log.d("SplashActivity", "Token ditemukan: $token")

                // Redirect ke MainActivity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                // Log jika token tidak tersedia
                Log.d("SplashActivity", "Token tidak ditemukan, mengarahkan ke StartActivity.")

                // Redirect ke StartActivity
                startActivity(Intent(this@SplashActivity, StartActivity::class.java))
            }
            finish()
        }
    }
}

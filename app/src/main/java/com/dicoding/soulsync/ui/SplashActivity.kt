package com.dicoding.soulsync.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

        val userPreference = UserPreference.getInstance(applicationContext)

        lifecycleScope.launch {
            delay(3000) // Menunggu animasi splash selesai

            // Mengambil nilai dari Flow menggunakan firstOrNull
            val isLoggedIn = userPreference.isLoggedIn().firstOrNull() ?: false

            if (isLoggedIn) {
                // Jika sudah login, redirect ke MainActivity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                // Jika belum login, redirect ke StartActivity
                startActivity(Intent(this@SplashActivity, StartActivity::class.java))
            }
            finish()
        }
    }
}

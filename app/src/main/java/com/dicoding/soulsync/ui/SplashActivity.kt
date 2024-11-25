package com.dicoding.soulsync.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsync.MainActivity
import com.dicoding.soulsync.R
import com.dicoding.soulsync.start.StartActivity
import com.dicoding.soulsync.ui.login.LoginActivity
import com.dicoding.soulsync.utils.SessionManager

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sessionManager = SessionManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            // Cek status login menggunakan SessionManager
            if (sessionManager.isLoggedIn()) {
                // Jika sudah login, arahkan ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Jika belum login, arahkan ke LoginActivity
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
            }
            finish() // Tutup SplashActivity
        }, 3000) // Durasi splash screen 3 detik
    }
}

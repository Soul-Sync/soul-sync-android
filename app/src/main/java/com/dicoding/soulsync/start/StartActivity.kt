package com.dicoding.soulsync.start


import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsync.MainActivity
import com.dicoding.soulsync.R
import com.dicoding.soulsync.ui.login.LoginActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val startButton: Button = findViewById(R.id.startButton)
        val appImageView = findViewById<View>(R.id.appImageView)

        // Animasi gambar bergerak ke kiri dan kanan dengan efek berulang
        val imageAnimator = ObjectAnimator.ofFloat(appImageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000  // Durasi animasi 6 detik
            repeatCount = ObjectAnimator.INFINITE  // Ulang terus-menerus
            repeatMode = ObjectAnimator.REVERSE  // Kembali ke posisi awal setelah gerakan
        }
        imageAnimator.start()

        startButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

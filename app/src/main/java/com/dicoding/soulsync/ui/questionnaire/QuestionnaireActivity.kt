package com.dicoding.soulsync.ui.questionnaire

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsync.R
import com.dicoding.soulsync.databinding.ActivityQuestionnaireBinding
import com.dicoding.soulsync.ui.question.QuestionActivity


class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionnaireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack2.setOnClickListener {
            onBackPressed()
        }

        // Tombol Mulai Kuisioner
        binding.startKuisioner.setOnClickListener {
            Toast.makeText(this, "Memulai Kuisioner...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}

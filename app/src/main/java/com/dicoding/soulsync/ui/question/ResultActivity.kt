package com.dicoding.soulsync.ui.question

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.soulsync.R
import com.dicoding.soulsync.ui.questionnaire.StartQuestActivity
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ResultActivity : AppCompatActivity() {

    private lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val questionnaireId = intent.getStringExtra("QUESTIONNAIRE_ID") ?: ""
        if (questionnaireId.isEmpty()) {
            Toast.makeText(this, "Invalid questionnaire ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val token = runBlocking {
            UserPreference(this@ResultActivity).token.first()
        }

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        Log.d("ResultActivity", "Using Token: $token")

        viewModel = ViewModelProvider(
            this,
            QuestionViewModelFactory(token)
        )[QuestionViewModel::class.java]

        observeViewModel()
        viewModel.fetchQuestionnaireById(questionnaireId)

        // Tombol back di header
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, StartQuestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, StartQuestActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun observeViewModel() {
        viewModel.submissionResult.observe(this) { result ->
            result?.payload?.questionnaire?.let { questionnaire ->
                findViewById<TextView>(R.id.tvStatus).text = "Status: ${questionnaire.status}"

                val musicRecommendations = questionnaire.music_recommendation.joinToString("\n") {
                    "- ${it.title} by ${it.artist}"
                }
                findViewById<TextView>(R.id.tvMusicRecommendations).text =
                    "$musicRecommendations"

                val therapyRecommendations = questionnaire.theraphy_recommendation.joinToString("\n") {
                    "- ${it.name}"
                }
                findViewById<TextView>(R.id.tvTherapyRecommendations).text =
                    "$therapyRecommendations"
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Log.e("ResultActivity", "Error: $errorMessage")
            Toast.makeText(this, "Failed to fetch data: $errorMessage", Toast.LENGTH_LONG).show()
        }
    }
}


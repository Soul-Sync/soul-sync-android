package com.dicoding.soulsync.ui.question

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.soulsync.R
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

        // Ambil token dari UserPreference
        val token = runBlocking {
            UserPreference(this@ResultActivity).token.first()
        }

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        Log.d("ResultActivity", "Using Token: $token")

        // Initialize ViewModel
        viewModel = ViewModelProvider(
            this,
            QuestionViewModelFactory(token)
        )[QuestionViewModel::class.java]

        // Observasi data dan error
        observeViewModel()

        // Fetch questionnaire details
        Log.d("ResultActivity", "Fetching Questionnaire ID: $questionnaireId")
        viewModel.fetchQuestionnaireById(questionnaireId)
    }

    private fun observeViewModel() {
        viewModel.submissionResult.observe(this) { result ->
            result?.payload?.questionnaire?.let { questionnaire ->
                Log.d("ResultActivity", "Questionnaire Data: $questionnaire")

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

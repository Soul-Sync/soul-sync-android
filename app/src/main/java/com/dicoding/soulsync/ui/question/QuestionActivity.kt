package com.dicoding.soulsync.ui.question

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class QuestionActivity : AppCompatActivity() {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: QuestionAdapter
    private val answers = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar) // Referensi ProgressBar
        recyclerView.layoutManager = LinearLayoutManager(this)

        val token = runBlocking {
            UserPreference(this@QuestionActivity).token.first() ?: ""
        }
        Log.d("QuestionActivity", "Token: $token")

        viewModel = ViewModelProvider(this, QuestionViewModelFactory(token))[QuestionViewModel::class.java]

        adapter = QuestionAdapter { keyword, answer ->
            answers[keyword] = answer
        }
        recyclerView.adapter = adapter

        // Tampilkan ProgressBar sebelum data dimuat
        showLoading(true)

        observeViewModel()
        viewModel.fetchQuestions()

        findViewById<Button>(R.id.submitButton).setOnClickListener {
            if (answers.size == adapter.itemCount) {
                // Tampilkan ProgressBar
                showLoading(true)
                viewModel.submitAnswers(answers)
            } else {
                Toast.makeText(this, "Jawab semua pertanyaan sebelum mengirim", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.questions.observe(this) { response ->
            if (response != null) {
                // Data berhasil diambil
                adapter.submitList(response.payload.question)
                showLoading(false) // Sembunyikan ProgressBar
                Log.d("QuestionActivity", "Questions loaded: ${response.payload.question.size} items")
            } else {
                // Data belum ada, tetap tampilkan loading
                showLoading(true)
            }
        }

        viewModel.submissionResult.observe(this) { result ->
            if (result != null) {
                // Sembunyikan ProgressBar setelah pengiriman berhasil
                showLoading(false)
                Toast.makeText(this, "Jawaban berhasil dikirim: ${result.message}", Toast.LENGTH_LONG).show()
                Log.d("QuestionActivity", "Submission success: ${result.message}")

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("QUESTIONNAIRE_ID", result.payload?.questionnaire?.id)
                startActivity(intent)
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            // Sembunyikan ProgressBar saat terjadi kesalahan
            showLoading(false)
            Toast.makeText(this, "Terjadi kesalahan: $errorMessage. Silakan coba lagi.", Toast.LENGTH_LONG).show()
            Log.e("QuestionActivity", "Error: $errorMessage")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        findViewById<Button>(R.id.submitButton).visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}

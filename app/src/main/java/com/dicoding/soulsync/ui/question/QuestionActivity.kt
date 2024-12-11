package com.dicoding.soulsync.ui.question

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
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
    private lateinit var adapter: QuestionAdapter
    private val answers = mutableMapOf<String, Int>() // Jawaban pengguna

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val token = runBlocking {
            UserPreference(this@QuestionActivity).token.first() ?: ""
        }
        // Tampilkan token di Logcat
        Log.d("QuestionActivity", "Token: $token")


        viewModel = ViewModelProvider(this, QuestionViewModelFactory(token))[QuestionViewModel::class.java]

        adapter = QuestionAdapter { keyword, answer ->
            answers[keyword] = answer  // Simpan jawaban berdasarkan keyword
        }
        recyclerView.adapter = adapter

        observeViewModel()
        viewModel.fetchQuestions()

        findViewById<Button>(R.id.submitButton).setOnClickListener {
            if (answers.size == adapter.itemCount) {
                viewModel.submitAnswers(answers)
            } else {
                Toast.makeText(this, "Jawab semua pertanyaan sebelum mengirim", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.questions.observe(this) { response ->
            response?.let {
                adapter.submitList(it.payload.question)
                Log.d("QuestionActivity", "Questions loaded: ${it.payload.question.size} items")
            }
        }

        viewModel.submissionResult.observe(this) { result ->
            result?.let {
                Toast.makeText(this, "Jawaban berhasil dikirim: ${it.message}", Toast.LENGTH_LONG).show()
                Log.d("QuestionActivity", "Submission success: ${it.message}")
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            Log.e("QuestionActivity", "Error: $errorMessage")
        }
    }
}

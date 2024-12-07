package com.dicoding.soulsync.ui.question

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.api.ApiService
import com.dicoding.soulsync.ui.question.QuestionAdapter
import com.dicoding.soulsync.databinding.ActivityQuestionBinding
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private lateinit var apiService: ApiService
    private lateinit var adapter: QuestionAdapter // Deklarasi adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView dan pasang adapter kosong
        adapter = QuestionAdapter(emptyList())
        binding.questionRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.questionRecyclerView.adapter = adapter

        lifecycleScope.launch {
            val userPreference = UserPreference(this@QuestionActivity)
            val token = userPreference.token.first() // Ambil token dari DataStore

            if (token.isNullOrEmpty()) {
                Toast.makeText(this@QuestionActivity, "Token tidak ditemukan, silakan login ulang", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val apiClient = ApiClient(token)
                apiService = apiClient.createService()
                fetchQuestions()
            }
        }
    }

    private fun fetchQuestions() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = apiService.getQuestions()
                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val questions = response.body()?.payload?.question ?: emptyList()
                    adapter.updateData(questions) // Perbarui data adapter
                } else {
                    Toast.makeText(this@QuestionActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@QuestionActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

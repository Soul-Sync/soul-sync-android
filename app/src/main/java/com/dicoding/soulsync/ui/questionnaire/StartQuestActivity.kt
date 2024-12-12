package com.dicoding.soulsync.ui.questionnaire


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.soulsync.api.ApiClient
import com.dicoding.soulsync.databinding.ActivityStartQuestBinding
import com.dicoding.soulsync.ui.question.QuestionActivity
import com.dicoding.soulsync.utils.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class StartQuestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartQuestBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil token
        val token = runBlocking {
            UserPreference(this@StartQuestActivity).token.first() ?: ""
        }

        if (token.isBlank()) {
            Toast.makeText(this, "Token tidak ditemukan. Harap login kembali.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("StartQuestActivity", "Retrieved token: $token")

        // Inisialisasi ApiClient, Repository, dan ViewModel
        val apiClient = ApiClient(token)
        val repository = HistoryRepository(apiClient.createService())
        val factory = HistoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        // Setup Adapter dan RecyclerView
        adapter = HistoryAdapter()
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistory.adapter = adapter

        // Observe ViewModel
        observeViewModel()

        // Fetch history data
        viewModel.fetchHistory()

        // Tombol kembali
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

    private fun observeViewModel() {
        viewModel.history.observe(this) { history ->
            adapter.submitList(history)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                Log.e("StartQuestActivity", "Error: $it")
            }
        }
    }
}

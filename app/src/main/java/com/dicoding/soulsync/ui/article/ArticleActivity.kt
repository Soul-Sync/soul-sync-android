package com.dicoding.soulsync.ui.article

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.soulsync.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeData()
        viewModel.fetchArticles()
    }

    private fun setupUI() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Tombol search untuk menampilkan EditText
        binding.btnSearch.setOnClickListener {
            toggleSearchBarVisibility()
        }

        // Setup RecyclerView
        adapter = ArticleAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun toggleSearchBarVisibility() {
        if (binding.etSearch.visibility == View.GONE) {
            binding.etSearch.visibility = View.VISIBLE
            binding.etSearch.requestFocus()
        } else {
            binding.etSearch.visibility = View.GONE
            binding.etSearch.text.clear()
        }
    }

    // Mengamati data artikel
    private fun observeData() {
        viewModel.articles.observe(this) { articles ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.updateData(articles)
        }
    }

    // Fungsi untuk melakukan pencarian
    private fun performSearch(query: String) {
        val filteredArticles = viewModel.articles.value?.filter {
            it.title.contains(query, ignoreCase = true)
        } ?: emptyList()

        if (filteredArticles.isEmpty()) {
            binding.tvNoResults.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.tvNoResults.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.updateData(filteredArticles)
        }
    }
}

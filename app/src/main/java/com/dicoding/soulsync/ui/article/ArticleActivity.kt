package com.dicoding.soulsync.ui.article

import android.os.Bundle
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

        adapter = ArticleAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Tampilkan ProgressBar sebelum data dimuat
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

        viewModel.articles.observe(this) { articles ->
            // Setelah data dimuat, sembunyikan ProgressBar dan tampilkan RecyclerView
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE

            // Update data adapter
            adapter.updateData(articles)
        }

        viewModel.fetchArticles()
    }
}

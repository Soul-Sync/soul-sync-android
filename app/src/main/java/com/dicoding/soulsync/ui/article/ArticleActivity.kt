package com.dicoding.soulsync.ui.article

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.api.ApiConfig
import com.dicoding.soulsync.model.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleActivity : AppCompatActivity() {

    private lateinit var rvArticles: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        rvArticles = findViewById(R.id.rvArticles)
        progressBar = findViewById(R.id.progressBar)

        articleAdapter = ArticleAdapter(emptyList())
        rvArticles.layoutManager = LinearLayoutManager(this)
        rvArticles.adapter = articleAdapter

        fetchArticles()
    }

    private fun fetchArticles() {
        progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService(this).getAllArticles()

        client.enqueue(object : Callback<List<Article>> {
            override fun onResponse(
                call: Call<List<Article>>,
                response: Response<List<Article>>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val articles = response.body() ?: emptyList()
                    articleAdapter.updateData(articles)
                }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                progressBar.visibility = View.GONE
                // Tampilkan pesan kesalahan
            }
        })
    }
}

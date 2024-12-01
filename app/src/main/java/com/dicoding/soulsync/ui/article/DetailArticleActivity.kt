package com.dicoding.soulsync.ui.article

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.soulsync.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan URL dari Intent
        val articleUrl = intent.getStringExtra(EXTRA_URL)

        if (articleUrl != null) {
            setupWebView(articleUrl)
        } else {
            finish() // Tutup activity jika URL tidak valid
        }
    }

    private fun setupWebView(url: String) {
        binding.progressBar.visibility = android.view.View.VISIBLE // Tampilkan ProgressBar

        binding.webView.apply {
            settings.javaScriptEnabled = true // Aktifkan JavaScript jika diperlukan
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = android.view.View.GONE // Sembunyikan ProgressBar
                }
            }
            webChromeClient = WebChromeClient() // Untuk mendukung elemen modern
            loadUrl(url)
        }
    }

    companion object {
        const val EXTRA_URL = "extra_url"
    }
}

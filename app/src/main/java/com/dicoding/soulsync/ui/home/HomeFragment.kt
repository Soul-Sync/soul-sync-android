package com.dicoding.soulsync.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dicoding.soulsync.R
import com.dicoding.soulsync.databinding.FragmentHomeBinding
import com.dicoding.soulsync.databinding.ItemArticleCardBinding
import com.dicoding.soulsync.model.Article
import com.dicoding.soulsync.ui.article.ArticleActivity
import com.dicoding.soulsync.ui.article.ArticleViewModel
import com.dicoding.soulsync.ui.article.DetailArticleActivity
import com.dicoding.soulsync.ui.questionnaire.StartQuestActivity
import com.dicoding.soulsync.utils.UserPreference
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference(requireContext())

        // Tampilkan nama pengguna
        observeUserName()

        // Tombol untuk memulai kuisioner
        binding.btnStart.setOnClickListener {
            val intent = Intent(requireContext(), StartQuestActivity::class.java)
            startActivity(intent)
        }

        // Observe articles dari ViewModel
        articleViewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles.isEmpty()) {
                binding.progressBarArticles.visibility = View.VISIBLE
            } else {
                binding.progressBarArticles.visibility = View.GONE
                val topArticles = articles.take(12) // Ambil 12 artikel teratas
                displayArticles(topArticles)
            }
        }

        // Fetch articles dari API
        articleViewModel.fetchArticles()

        // Tombol "See All" untuk membuka ArticleActivity
        binding.tvSeeAll.setOnClickListener {
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeUserName() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userPreference.name.collect { name ->
                    binding?.tvGreeting?.text = "Hi ${name ?: ""} \uD83D\uDE0A"
                }
            }
        }
    }

    private fun displayArticles(articles: List<Article>) {
        // Sembunyikan ProgressBar setelah artikel dimuat
        binding.progressBarArticles.visibility = View.GONE

        binding.articlesContainer.removeAllViews() // Hapus artikel lama sebelum menambahkan yang baru

        for (article in articles) {
            val articleBinding = ItemArticleCardBinding.inflate(
                LayoutInflater.from(requireContext()),
                binding.articlesContainer,
                false
            )

            // Isi data artikel ke layout
            articleBinding.tvTitle.text = article.title
            articleBinding.tvSource.text = article.source.name
            Picasso.get()
                .load(article.thumbnail)
                .placeholder(R.drawable.image)
                .into(articleBinding.ivThumbnail)

            // Set klik listener untuk navigasi ke DetailArticleActivity
            articleBinding.root.setOnClickListener {
                val intent = Intent(requireContext(), DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.EXTRA_URL, article.link)
                startActivity(intent)
            }

            // Tambahkan ke container
            binding.articlesContainer.addView(articleBinding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

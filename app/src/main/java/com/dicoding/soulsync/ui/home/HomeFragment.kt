package com.dicoding.soulsync.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.soulsync.R
import com.dicoding.soulsync.databinding.FragmentHomeBinding
import com.dicoding.soulsync.databinding.ItemArticleCardBinding
import com.dicoding.soulsync.model.Article
import com.dicoding.soulsync.ui.article.ArticleActivity
import com.dicoding.soulsync.ui.article.ArticleViewModel
import com.dicoding.soulsync.ui.article.DetailArticleActivity
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        articleViewModel.articles.observe(viewLifecycleOwner) { articles ->
            val topArticles = articles.take(8)
            displayArticles(topArticles)
        }


        articleViewModel.fetchArticles()


        binding.tvSeeAll.setOnClickListener {
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayArticles(articles: List<Article>) {
        binding.articlesContainer.removeAllViews()

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


            articleBinding.root.setOnClickListener {
                val intent = Intent(requireContext(), DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.EXTRA_URL, article.link)
                startActivity(intent)
            }

            binding.articlesContainer.addView(articleBinding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

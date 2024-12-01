package com.dicoding.soulsync.ui.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R
import com.dicoding.soulsync.databinding.ItemArticleBinding
import com.dicoding.soulsync.model.Article
import com.squareup.picasso.Picasso

class ArticleAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvSource.text = article.source.name

            // Memuat gambar dengan Picasso
            Picasso.get()
                .load(article.thumbnail)
                .placeholder(R.drawable.image)
                .into(binding.ivThumbnail)

            // Tambahkan klik listener
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.EXTRA_URL, article.link)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = articles.size
}

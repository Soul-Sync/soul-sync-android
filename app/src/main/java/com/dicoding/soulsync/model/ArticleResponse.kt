package com.dicoding.soulsync.model

data class ArticleResponse(
    val news_results: List<Article>
)

data class Article(
    val title: String,
    val link: String,
    val source: Source,
    val date: String,
    val thumbnail: String? // URL gambar, bisa null
)

data class Source(
    val name: String,
    val icon: String  // Menyimpan URL ikon jika perlu digunakan
)



package com.example.newsappwithkotlinjetpack.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("articles")
    val articles: List<ArticleItem>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)
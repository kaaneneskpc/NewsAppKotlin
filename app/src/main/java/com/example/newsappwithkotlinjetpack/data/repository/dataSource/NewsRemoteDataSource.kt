package com.example.newsappwithkotlinjetpack.data.repository.dataSource

import com.example.newsappwithkotlinjetpack.data.model.Article
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<Article>
    suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Response<Article>
}
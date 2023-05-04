package com.example.newsappwithkotlinjetpack.data.service.repository

import com.example.newsappwithkotlinjetpack.data.model.Article
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import com.example.newsappwithkotlinjetpack.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(country: String, page: Int): Resource<Article>
    suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<Article>
    suspend fun saveNews(articleItem: ArticleItem)
    suspend fun deleteNews(articleItem: ArticleItem)
    fun getSavedNews(): Flow<List<ArticleItem>>
}
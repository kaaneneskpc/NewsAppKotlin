package com.example.newsappwithkotlinjetpack.data.service.usecase

import com.example.newsappwithkotlinjetpack.data.model.Article
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import com.example.newsappwithkotlinjetpack.data.utils.Resource
import com.example.newsappwithkotlinjetpack.data.service.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsUseCase(private val repository: NewsRepository) {

    suspend fun executeNewsHeadlines(country: String, page: Int): Resource<Article> {
        return repository.getNewsHeadlines(country, page)
    }

    suspend fun executeSearchedNews(country: String, searchQuery: String, page: Int): Resource<Article> {
        return repository.getSearchedNews(country, searchQuery, page)
    }

    suspend fun executeSaveNews(articleItem: ArticleItem) = repository.saveNews(articleItem)

    suspend fun executeDeleteNews(articleItem: ArticleItem) = repository.deleteNews(articleItem)

    fun executeGetSavedNews(): Flow<List<ArticleItem>> { return repository.getSavedNews() }
}
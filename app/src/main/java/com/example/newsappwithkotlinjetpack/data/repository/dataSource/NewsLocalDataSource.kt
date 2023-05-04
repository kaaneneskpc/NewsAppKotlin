package com.example.newsappwithkotlinjetpack.data.repository.dataSource

import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(articleItem: ArticleItem)
    fun getSavedArticles(): Flow<List<ArticleItem>>
    suspend fun deleteArticleFromDB(articleItem: ArticleItem)
}
package com.example.newsappwithkotlinjetpack.data.repository.dataSourceImpl

import com.example.newsappwithkotlinjetpack.data.db.ArticleDAO
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import com.example.newsappwithkotlinjetpack.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDAO: ArticleDAO): NewsLocalDataSource {
    override suspend fun saveArticleToDB(articleItem: ArticleItem) {
        articleDAO.insert(articleItem)
    }

    override fun getSavedArticles(): Flow<List<ArticleItem>> {
        return articleDAO.getAllArticles()
    }

    override suspend fun deleteArticleFromDB(articleItem: ArticleItem) {
        articleDAO.deleteArticle(articleItem)
    }
}
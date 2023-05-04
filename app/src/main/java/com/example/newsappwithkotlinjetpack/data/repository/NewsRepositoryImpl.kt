package com.example.newsappwithkotlinjetpack.data.repository

import com.example.newsappwithkotlinjetpack.data.model.Article
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import com.example.newsappwithkotlinjetpack.data.repository.dataSource.NewsLocalDataSource
import com.example.newsappwithkotlinjetpack.data.repository.dataSource.NewsRemoteDataSource
import com.example.newsappwithkotlinjetpack.data.utils.Resource
import com.example.newsappwithkotlinjetpack.data.service.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource, private val newsLocalDataSource: NewsLocalDataSource): NewsRepository {
    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<Article> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<Article> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(country, searchQuery, page))
    }

    override suspend fun saveNews(articleItem: ArticleItem) {
        newsLocalDataSource.saveArticleToDB(articleItem)
    }

    override suspend fun deleteNews(articleItem: ArticleItem) {
        newsLocalDataSource.deleteArticleFromDB(articleItem)
    }

    override fun getSavedNews(): Flow<List<ArticleItem>> {
        return newsLocalDataSource.getSavedArticles()
    }

    private fun responseToResource(response: Response<Article>): Resource<Article> {
        response.takeIf { it.isSuccessful }.apply {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}
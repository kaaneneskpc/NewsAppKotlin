package com.example.newsappwithkotlinjetpack.data.repository.dataSourceImpl

import com.example.newsappwithkotlinjetpack.data.api.NewsAPI
import com.example.newsappwithkotlinjetpack.data.model.Article
import com.example.newsappwithkotlinjetpack.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsAPI
): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<Article> {
        return newsApiService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Response<Article> {
        return newsApiService.getSearchedTopHeadlines(country, searchQuery, page)
    }
}
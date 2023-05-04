package com.example.newsappwithkotlinjetpack.data.api

import com.example.newsappwithkotlinjetpack.data.model.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.newsappwithkotlinjetpack.BuildConfig

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<Article>

    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<Article>
}
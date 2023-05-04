package com.example.newsappwithkotlinjetpack.di

import com.example.newsappwithkotlinjetpack.data.api.NewsAPI
import com.example.newsappwithkotlinjetpack.data.repository.dataSource.NewsRemoteDataSource
import com.example.newsappwithkotlinjetpack.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NewsRemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPI: NewsAPI): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPI)
    }

}
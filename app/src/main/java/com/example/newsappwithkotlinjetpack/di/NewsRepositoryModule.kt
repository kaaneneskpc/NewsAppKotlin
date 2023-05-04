package com.example.newsappwithkotlinjetpack.di

import com.example.newsappwithkotlinjetpack.data.repository.NewsRepositoryImpl
import com.example.newsappwithkotlinjetpack.data.repository.dataSource.NewsLocalDataSource
import com.example.newsappwithkotlinjetpack.data.repository.dataSource.NewsRemoteDataSource
import com.example.newsappwithkotlinjetpack.data.service.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NewsRepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource, newsLocalDataSource: NewsLocalDataSource): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }

}
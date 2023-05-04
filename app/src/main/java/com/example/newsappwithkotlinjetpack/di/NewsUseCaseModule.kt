package com.example.newsappwithkotlinjetpack.di

import com.example.newsappwithkotlinjetpack.data.service.repository.NewsRepository
import com.example.newsappwithkotlinjetpack.data.service.usecase.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NewsUseCaseModule {

    @Singleton
    @Provides
    fun provideNewsUseCase(newsRepository: NewsRepository): NewsUseCase {
        return NewsUseCase(newsRepository)
    }

}
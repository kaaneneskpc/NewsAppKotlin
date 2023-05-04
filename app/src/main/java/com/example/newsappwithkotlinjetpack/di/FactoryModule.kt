package com.example.newsappwithkotlinjetpack.di

import android.app.Application
import com.example.newsappwithkotlinjetpack.viewModel.NewsViewModelFactory
import com.example.newsappwithkotlinjetpack.data.service.usecase.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsUseCase(app: Application, newsUseCase: NewsUseCase): NewsViewModelFactory {
        return NewsViewModelFactory(app, newsUseCase)
    }

}
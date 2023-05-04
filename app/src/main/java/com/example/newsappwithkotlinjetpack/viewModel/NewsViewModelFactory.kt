package com.example.newsappwithkotlinjetpack.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappwithkotlinjetpack.data.service.usecase.NewsUseCase

class NewsViewModelFactory(
    private val app: Application,
    private val newsUseCase: NewsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsUseCase) as T
    }
}
package com.example.newsappwithkotlinjetpack.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappwithkotlinjetpack.data.model.Article
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import com.example.newsappwithkotlinjetpack.data.utils.Resource
import com.example.newsappwithkotlinjetpack.data.service.usecase.NewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class NewsViewModel(private val app: Application, private val newsUseCase: NewsUseCase): AndroidViewModel(app) {

    private val _newsHeadlines = MutableStateFlow<Resource<Article>?>(null)
    var newsHeadlines = _newsHeadlines.asStateFlow()

    private val _recyclerViewProgressBarVisibility = MutableStateFlow(false)
    var recyclerViewProgressBarVisibility = _recyclerViewProgressBarVisibility.asStateFlow()

    private val _searchedNews = MutableStateFlow<Resource<Article>?>(null)
    var searchedNews = _searchedNews.asStateFlow()

    var country: String = "us"
    var page: Int = 1

    fun recyclerViewProgressBarVisibility(isChanged: Boolean) {
        _recyclerViewProgressBarVisibility.value = isChanged
    }

    fun searchNews(country: String, searchQuery: String, page: Int) = viewModelScope.launch {
        _searchedNews.value = Resource.Loading()
        (isNetworkAvailable(app)).takeIf { it == true }.apply {
            val result = newsUseCase.executeSearchedNews(country, searchQuery, page)
            _searchedNews.value = result
        }.run {
            _searchedNews.value = Resource.Error("Internet is not available")
        }
    }

    //local data
    fun saveArticle(articleItem: ArticleItem) = viewModelScope.launch {
        newsUseCase.executeSaveNews(articleItem)
    }

    fun deleteArticle(articleItem: ArticleItem) = viewModelScope.launch {
        newsUseCase.executeDeleteNews(articleItem)
    }

    fun getSavedArticles() = flow {
        newsUseCase.executeGetSavedNews().collect {
            emit(it)
        }
    }


    fun getNewsHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        _newsHeadlines.value = Resource.Loading()
        (isNetworkAvailable(app)).takeIf { it == true }.apply {
            val result = newsUseCase.executeNewsHeadlines(country, page)
            _newsHeadlines.value = result
        }.run {
            _newsHeadlines.value = Resource.Error("Internet is not available")
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}
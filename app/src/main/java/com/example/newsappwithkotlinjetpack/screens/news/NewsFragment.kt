package com.example.newsappwithkotlinjetpack.screens.news

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappwithkotlinjetpack.MainActivity
import com.example.newsappwithkotlinjetpack.R
import com.example.newsappwithkotlinjetpack.adapter.NewsAdapter
import com.example.newsappwithkotlinjetpack.data.model.Article
import com.example.newsappwithkotlinjetpack.data.utils.Resource
import com.example.newsappwithkotlinjetpack.databinding.FragmentNewsBinding
import com.example.newsappwithkotlinjetpack.viewModel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsFragmentViewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        newsFragmentViewModel = (activity as MainActivity).newsViewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        navigateNewsDetail()
        initRecyclerView()
        viewNewsList()
        setSearchView()
    }

    private fun viewNewsList() {
        newsFragmentViewModel.apply {
            getNewsHeadlines(country, page)
            lifecycleScope.launch {
                newsHeadlines.collect { response ->
                    responseWhenCases(response)
                }
            }
        }
    }

    fun searchedNews() {
        lifecycleScope.launch {
            newsFragmentViewModel.searchedNews.collect { response ->
                responseWhenCases(response)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchView() {
        binding.searchNews.apply {

            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    newsFragmentViewModel.apply { searchNews(country, p0.toString(), page) }
                    searchedNews()
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    MainScope().launch {
                        delay(1500)
                        newsFragmentViewModel.apply { searchNews(country, p0.toString(), page) }
                        searchedNews()
                    }
                    return false
                }
            })

            setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    this.isIconified = false
                }
            }

            setOnCloseListener {
                initRecyclerView()
                viewNewsList()
                if (this.query.isNotEmpty() || this.query.isEmpty()) {
                    // Clear the searchView text
                    this.setQuery("", false)
                    // Hide the keyboard
                    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(this.windowToken, 0)
                }
                false
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun navigateNewsDetail() {
        newsAdapter.setOnItemClickListener { articleItem ->
            MainScope().launch {
                delay(1500)
                articleItem.takeIf { it != null }?.let {
                    if(!TextUtils.isEmpty(it.toString())) {
                        val bundle = Bundle().apply {
                            putSerializable("selected_article", articleItem)
                        }
                        findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
                    }
                }
            }
        }
    }

    private fun responseWhenCases(response: Resource<Article>?) {
        when(response) {
            is Resource.Success -> {
                newsFragmentViewModel.recyclerViewProgressBarVisibility(false)
                response.data?.let { article ->
                    newsAdapter.diffUtil.submitList(article.articles.toList())
                }
            }
            is Resource.Error -> {
                newsFragmentViewModel.recyclerViewProgressBarVisibility(true)
                response.message?.let {
                    Log.d("activity", "An error occurred: $it")
                }
            }
            is Resource.Loading -> {
                newsFragmentViewModel.recyclerViewProgressBarVisibility(true)
            }
            else -> {

            }
        }
    }
}
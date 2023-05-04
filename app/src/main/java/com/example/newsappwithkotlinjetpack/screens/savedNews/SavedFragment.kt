package com.example.newsappwithkotlinjetpack.screens.savedNews

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappwithkotlinjetpack.MainActivity
import com.example.newsappwithkotlinjetpack.R
import com.example.newsappwithkotlinjetpack.adapter.NewsAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappwithkotlinjetpack.databinding.FragmentSavedBinding
import com.example.newsappwithkotlinjetpack.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SavedFragment : Fragment() {
    private lateinit var savedAdapter: NewsAdapter
    private lateinit var newsFragmentViewModel: NewsViewModel
    private lateinit var binding: FragmentSavedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        newsFragmentViewModel = (activity as MainActivity).newsViewModel
        savedAdapter = (activity as MainActivity).newsAdapter
        navigateNewsDetail()
        initRecyclerView()
        viewSavedNewsList()
        swipeToDelete()
        navigateBeforePage()
    }

    private fun initRecyclerView() {
        binding.recyclerSavedNews.apply {
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun navigateBeforePage() {
        binding.imageViewArrowBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun viewSavedNewsList() {
        lifecycleScope.launch {
            newsFragmentViewModel.getSavedArticles().collect { response ->
                savedAdapter.diffUtil.submitList(response)
            }
        }
    }


    private fun navigateNewsDetail() {
        savedAdapter.setOnItemClickListener { articleItem ->
            MainScope().launch {
                delay(1500)
                articleItem.takeIf { it != null }?.let {
                    if(!TextUtils.isEmpty(it.toString())) {
                        val bundle = Bundle().apply {
                            putSerializable("selected_article", articleItem)
                        }
                        findNavController().navigate(R.id.action_savedFragment_to_infoFragment2, bundle)
                    }
                }
            }
        }
    }

    private fun swipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = savedAdapter.diffUtil.currentList[position]
                newsFragmentViewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                            setAction("Undo") { newsFragmentViewModel.saveArticle(article) }
                            show()
                      }
                  }
              }
          }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerSavedNews)
        }
    }
}
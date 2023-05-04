package com.example.newsappwithkotlinjetpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsappwithkotlinjetpack.databinding.FragmentInfoBinding
import com.example.newsappwithkotlinjetpack.viewModel.NewsViewModel

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var newsFragmentViewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)
        newsFragmentViewModel = (activity as MainActivity).newsViewModel
        val args: InfoFragmentArgs by navArgs()

        binding.apply {
            textViewTitle.text = args.selectedArticle.title ?: "No Title"
            textViewDescription.text = args.selectedArticle.description ?: "No Description"
            textViewPublishedAt.text = args.selectedArticle.publishedAt ?: "No Published Date"
            textViewSource.text = args.selectedArticle.source?.name ?: "No Source"

            // Load the image using Glide or any other image loading library
            Glide.with(requireContext())
                .load(args.selectedArticle.urlToImage)
                .into(imageViewArticleImage)

            floatingActionButtonSave.setOnClickListener {
                newsFragmentViewModel.saveArticle(args.selectedArticle)
                Toast.makeText(activity, "Saved Successfully", Toast.LENGTH_SHORT).show()
            }

            imageViewArrowBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }



}
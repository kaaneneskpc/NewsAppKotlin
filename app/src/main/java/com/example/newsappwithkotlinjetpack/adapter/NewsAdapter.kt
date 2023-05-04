package com.example.newsappwithkotlinjetpack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import com.example.newsappwithkotlinjetpack.databinding.NewsListItemBinding

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onItemClickListener: ((ArticleItem) -> Unit)? = null


    private val callback = object : DiffUtil.ItemCallback<ArticleItem>(){
        override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
            return oldItem == newItem
        }

    }

    val diffUtil = AsyncListDiffer(this,callback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }



    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = diffUtil.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    inner class NewsViewHolder(val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(articleItem: ArticleItem) {
            binding.apply {
                textViewTitle.text = articleItem.title
                textViewDescription.text = articleItem.description
                textViewPublishedAt.text = articleItem.publishedAt
                textViewSource.text = articleItem.source?.name
            }
            Glide.with(binding.imageViewArticleImage.context).
            load(articleItem.urlToImage).
            into(binding.imageViewArticleImage)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(articleItem)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (ArticleItem) -> Unit) {
        onItemClickListener = listener
    }
}
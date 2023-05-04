package com.example.newsappwithkotlinjetpack.data.db

import androidx.room.*
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleItem: ArticleItem)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ArticleItem>>

    @Delete
    suspend fun deleteArticle(articleItem: ArticleItem)

}
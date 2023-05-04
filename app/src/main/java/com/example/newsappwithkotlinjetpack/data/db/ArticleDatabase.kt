package com.example.newsappwithkotlinjetpack.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappwithkotlinjetpack.data.model.ArticleItem


@Database(
    entities = [ArticleItem::class],
    version = 1,
    exportSchema = false,
)

@TypeConverters(Converter::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDAO(): ArticleDAO
}
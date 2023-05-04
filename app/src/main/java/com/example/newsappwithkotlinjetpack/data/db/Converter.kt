package com.example.newsappwithkotlinjetpack.data.db

import androidx.room.TypeConverter
import com.example.newsappwithkotlinjetpack.data.model.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}
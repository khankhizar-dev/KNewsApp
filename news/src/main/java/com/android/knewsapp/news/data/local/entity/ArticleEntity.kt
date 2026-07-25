package com.android.knewsapp.news.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val author: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val sourceName: String,
    val country: String?,
    val category: String?
)

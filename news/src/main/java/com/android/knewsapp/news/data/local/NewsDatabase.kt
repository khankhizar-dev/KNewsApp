package com.android.knewsapp.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.knewsapp.news.data.local.dao.NewsDao
import com.android.knewsapp.news.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}

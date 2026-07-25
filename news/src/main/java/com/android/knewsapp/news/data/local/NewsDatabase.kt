package com.android.knewsapp.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.knewsapp.news.data.local.dao.NewsDao
import com.android.knewsapp.news.data.local.entity.ArticleEntity
import com.android.knewsapp.news.data.local.entity.BookmarkEntity

@Database(
    entities = [ArticleEntity::class, BookmarkEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}

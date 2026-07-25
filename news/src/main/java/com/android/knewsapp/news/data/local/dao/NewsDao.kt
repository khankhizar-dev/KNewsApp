package com.android.knewsapp.news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.knewsapp.news.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM articles WHERE (:country IS NULL OR country = :country) AND (:category IS NULL OR category = :category) ORDER BY publishedAt DESC")
    fun getArticles(country: String?, category: String?): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE (:country IS NULL OR country = :country) AND (:category IS NULL OR category = :category)")
    suspend fun clearArticles(country: String?, category: String?)
}

package com.android.knewsapp.news.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.knewsapp.news.data.local.entity.ArticleEntity
import com.android.knewsapp.news.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query(
        """
        SELECT * FROM articles 
        WHERE (:country IS NULL OR country = :country) 
        AND (:category IS NULL OR category = :category) 
        ORDER BY publishedAt DESC
        """,
    )
    fun getArticles(
        country: String?,
        category: String?,
    ): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query(
        """
        DELETE FROM articles 
        WHERE (:country IS NULL OR country = :country) 
        AND (:category IS NULL OR category = :category)
        """,
    )
    suspend fun clearArticles(
        country: String?,
        category: String?,
    )

    // Bookmark operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks ORDER BY bookmarkedAt DESC")
    fun getBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE url = :url)")
    fun isBookmarked(url: String): Flow<Boolean>
}

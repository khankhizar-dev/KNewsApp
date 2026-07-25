package com.android.knewsapp.news.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.android.knewsapp.news.data.local.NewsDatabase
import com.android.knewsapp.news.data.local.entity.ArticleEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NewsDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var dao: NewsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.newsDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `insert and get articles`() = runTest {
        val articles = listOf(
            ArticleEntity("url1", "Title 1", null, null, null, "2024-01-01", null, "Source", "us", null)
        )
        dao.insertArticles(articles)
        
        val result = dao.getArticles("us", null).first()
        assertThat(result).hasSize(1)
        assertThat(result[0].url).isEqualTo("url1")
    }

    @Test
    fun `clear articles removes only matching`() = runTest {
        val articles = listOf(
            ArticleEntity("url1", "Title 1", null, null, null, "2024-01-01", null, "Source", "us", null),
            ArticleEntity("url2", "Title 2", null, null, null, "2024-01-01", null, "Source", "gb", null)
        )
        dao.insertArticles(articles)
        
        dao.clearArticles("us", null)
        
        val usResult = dao.getArticles("us", null).first()
        val gbResult = dao.getArticles("gb", null).first()
        
        assertThat(usResult).isEmpty()
        assertThat(gbResult).hasSize(1)
    }
}

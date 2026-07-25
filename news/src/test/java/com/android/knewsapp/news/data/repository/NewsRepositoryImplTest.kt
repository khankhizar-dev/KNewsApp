package com.android.knewsapp.news.data.repository

import app.cash.turbine.test
import com.android.knewsapp.news.data.local.dao.NewsDao
import com.android.knewsapp.news.data.remote.NewsApiService
import com.android.knewsapp.news.domain.model.NewsResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NewsRepositoryImplTest {

    private lateinit var repository: NewsRepositoryImpl
    private val apiService: NewsApiService = mockk()
    private val newsDao: NewsDao = mockk(relaxed = true)

    @Before
    fun setup() {
        repository = NewsRepositoryImpl(apiService, newsDao)
    }

    @Test
    fun `getArticles emits failure when remote fetch fails`() = runTest {
        every { newsDao.getArticles(any(), any()) } returns flowOf(emptyList())
        coEvery { apiService.getTopHeadlines(any(), any(), any(), any()) } throws Exception("API Error")
        
        repository.getArticles("us", null, fetchFromRemote = true).test {
            val result = awaitItem()
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()?.message).isEqualTo("API Error")
            
            // It will continue to collect from Dao
            cancelAndIgnoreRemainingEvents()
        }
    }
}

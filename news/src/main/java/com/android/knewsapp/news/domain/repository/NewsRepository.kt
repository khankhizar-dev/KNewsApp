package com.android.knewsapp.news.domain.repository

import com.android.knewsapp.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getArticles(
        country: String? = null,
        category: String? = null,
        fetchFromRemote: Boolean = false
    ): Flow<Result<List<Article>>>

    suspend fun getTopHeadlines(
        country: String? = null,
        category: String? = null
    ): Result<List<Article>>

    suspend fun getEverything(
        query: String,
        language: String? = null
    ): Result<List<Article>>
}

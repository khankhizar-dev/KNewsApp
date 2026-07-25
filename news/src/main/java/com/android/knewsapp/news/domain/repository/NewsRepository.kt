package com.android.knewsapp.news.domain.repository

import com.android.knewsapp.news.domain.model.Article

interface NewsRepository {
    suspend fun getTopHeadlines(
        country: String? = null,
        category: String? = null
    ): Result<List<Article>>

    suspend fun getEverything(
        query: String,
        language: String? = null
    ): Result<List<Article>>
}

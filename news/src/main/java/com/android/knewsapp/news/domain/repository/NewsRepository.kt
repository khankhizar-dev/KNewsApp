package com.android.knewsapp.news.domain.repository

import com.android.knewsapp.news.domain.model.Article

interface NewsRepository {
    suspend fun getTopHeadlines(): Result<List<Article>>
}

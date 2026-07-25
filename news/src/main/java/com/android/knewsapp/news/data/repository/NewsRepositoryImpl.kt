package com.android.knewsapp.news.data.repository

import com.android.knewsapp.news.BuildConfig
import com.android.knewsapp.news.data.remote.NewsApiService
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService
) : NewsRepository {
    
    override suspend fun getTopHeadlines(
        country: String?,
        category: String?
    ): Result<List<Article>> {
        return try {
            val response = apiService.getTopHeadlines(
                country = country,
                category = category,
                apiKey = BuildConfig.NEWS_API_KEY
            )
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEverything(
        query: String,
        language: String?
    ): Result<List<Article>> {
        return try {
            val response = apiService.getEverything(
                query = query,
                language = language,
                apiKey = BuildConfig.NEWS_API_KEY
            )
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

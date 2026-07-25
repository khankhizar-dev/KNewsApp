package com.android.knewsapp.news.data.remote

import com.android.knewsapp.news.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("q") query: String? = null,
        @Query("apiKey") apiKey: String,
    ): NewsResponse

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = "publishedAt",
        @Query("apiKey") apiKey: String,
    ): NewsResponse
}

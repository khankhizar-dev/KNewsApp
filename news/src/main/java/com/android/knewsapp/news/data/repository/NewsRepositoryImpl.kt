package com.android.knewsapp.news.data.repository

import com.android.knewsapp.news.BuildConfig
import com.android.knewsapp.news.data.local.dao.NewsDao
import com.android.knewsapp.news.data.mapper.toArticle
import com.android.knewsapp.news.data.mapper.toArticleEntity
import com.android.knewsapp.news.data.mapper.toBookmarkEntity
import com.android.knewsapp.news.data.remote.NewsApiService
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl
    @Inject
    constructor(
        private val apiService: NewsApiService,
        private val newsDao: NewsDao,
    ) : NewsRepository {
        override fun getArticles(
            country: String?,
            category: String?,
            fetchFromRemote: Boolean,
        ): Flow<Result<List<Article>>> =
            flow {
                if (fetchFromRemote) {
                    try {
                        val response =
                            apiService.getTopHeadlines(
                                country = country,
                                category = category,
                                apiKey = BuildConfig.NEWS_API_KEY,
                            )
                        newsDao.clearArticles(country, category)
                        newsDao.insertArticles(response.articles.map { it.toArticleEntity(country, category) })
                    } catch (e: Exception) {
                        emit(Result.failure(e))
                    }
                }

                // Combine local articles with bookmarks to set the isBookmarked flag
                combine(
                    newsDao.getArticles(country, category),
                    newsDao.getBookmarks(),
                ) { articles, bookmarks ->
                    val bookmarkedUrls = bookmarks.map { it.url }.toSet()
                    articles.map { it.toArticle(isBookmarked = bookmarkedUrls.contains(it.url)) }
                }.collect {
                    emit(Result.success(it))
                }
            }

        override suspend fun getTopHeadlines(
            country: String?,
            category: String?,
        ): Result<List<Article>> {
            return try {
                val response =
                    apiService.getTopHeadlines(
                        country = country,
                        category = category,
                        apiKey = BuildConfig.NEWS_API_KEY,
                    )
                Result.success(response.articles)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getEverything(
            query: String,
            language: String?,
        ): Result<List<Article>> {
            return try {
                val response =
                    apiService.getEverything(
                        query = query,
                        language = language,
                        apiKey = BuildConfig.NEWS_API_KEY,
                    )
                Result.success(response.articles)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun toggleBookmark(article: Article) {
            val isBookmarked = newsDao.isBookmarked(article.url).first()
            if (isBookmarked) {
                newsDao.deleteBookmark(article.toBookmarkEntity())
            } else {
                newsDao.insertBookmark(article.toBookmarkEntity())
            }
        }

        override fun getBookmarks(): Flow<List<Article>> {
            return newsDao.getBookmarks().map { entities ->
                entities.map { it.toArticle() }
            }
        }

        override fun isBookmarked(url: String): Flow<Boolean> {
            return newsDao.isBookmarked(url)
        }
    }

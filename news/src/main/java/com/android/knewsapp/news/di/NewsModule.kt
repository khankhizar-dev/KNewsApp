package com.android.knewsapp.news.di

import android.content.Context
import androidx.room.Room
import com.android.knewsapp.news.data.local.NewsDatabase
import com.android.knewsapp.news.data.local.dao.NewsDao
import com.android.knewsapp.news.data.remote.NewsApiService
import com.android.knewsapp.news.data.repository.NewsRepositoryImpl
import com.android.knewsapp.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context: Context,
    ): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: NewsDatabase): NewsDao {
        return database.newsDao
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofitBuilder: Retrofit.Builder): NewsApiService {
        return retrofitBuilder
            .baseUrl("https://newsapi.org/v2/")
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: NewsApiService,
        newsDao: NewsDao,
    ): NewsRepository {
        return NewsRepositoryImpl(apiService, newsDao)
    }
}

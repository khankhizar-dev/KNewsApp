package com.android.knewsapp.news.di

import com.android.knewsapp.news.data.remote.NewsApiService
import com.android.knewsapp.news.data.repository.NewsRepositoryImpl
import com.android.knewsapp.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

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
    fun provideNewsRepository(apiService: NewsApiService): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }
}

package com.android.knewsapp.news.data.repository;

import com.android.knewsapp.news.data.local.dao.NewsDao;
import com.android.knewsapp.news.data.remote.NewsApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class NewsRepositoryImpl_Factory implements Factory<NewsRepositoryImpl> {
  private final Provider<NewsApiService> apiServiceProvider;

  private final Provider<NewsDao> newsDaoProvider;

  public NewsRepositoryImpl_Factory(Provider<NewsApiService> apiServiceProvider,
      Provider<NewsDao> newsDaoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.newsDaoProvider = newsDaoProvider;
  }

  @Override
  public NewsRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), newsDaoProvider.get());
  }

  public static NewsRepositoryImpl_Factory create(Provider<NewsApiService> apiServiceProvider,
      Provider<NewsDao> newsDaoProvider) {
    return new NewsRepositoryImpl_Factory(apiServiceProvider, newsDaoProvider);
  }

  public static NewsRepositoryImpl newInstance(NewsApiService apiService, NewsDao newsDao) {
    return new NewsRepositoryImpl(apiService, newsDao);
  }
}

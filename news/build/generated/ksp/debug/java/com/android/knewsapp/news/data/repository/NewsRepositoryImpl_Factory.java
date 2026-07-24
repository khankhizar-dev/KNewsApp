package com.android.knewsapp.news.data.repository;

import com.android.knewsapp.news.data.remote.NewsApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class NewsRepositoryImpl_Factory implements Factory<NewsRepositoryImpl> {
  private final Provider<NewsApiService> apiServiceProvider;

  public NewsRepositoryImpl_Factory(Provider<NewsApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public NewsRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static NewsRepositoryImpl_Factory create(
      javax.inject.Provider<NewsApiService> apiServiceProvider) {
    return new NewsRepositoryImpl_Factory(Providers.asDaggerProvider(apiServiceProvider));
  }

  public static NewsRepositoryImpl_Factory create(Provider<NewsApiService> apiServiceProvider) {
    return new NewsRepositoryImpl_Factory(apiServiceProvider);
  }

  public static NewsRepositoryImpl newInstance(NewsApiService apiService) {
    return new NewsRepositoryImpl(apiService);
  }
}

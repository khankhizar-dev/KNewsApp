package com.android.knewsapp.news.di;

import com.android.knewsapp.news.data.local.dao.NewsDao;
import com.android.knewsapp.news.data.remote.NewsApiService;
import com.android.knewsapp.news.domain.repository.NewsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NewsModule_ProvideNewsRepositoryFactory implements Factory<NewsRepository> {
  private final Provider<NewsApiService> apiServiceProvider;

  private final Provider<NewsDao> newsDaoProvider;

  public NewsModule_ProvideNewsRepositoryFactory(Provider<NewsApiService> apiServiceProvider,
      Provider<NewsDao> newsDaoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.newsDaoProvider = newsDaoProvider;
  }

  @Override
  public NewsRepository get() {
    return provideNewsRepository(apiServiceProvider.get(), newsDaoProvider.get());
  }

  public static NewsModule_ProvideNewsRepositoryFactory create(
      javax.inject.Provider<NewsApiService> apiServiceProvider,
      javax.inject.Provider<NewsDao> newsDaoProvider) {
    return new NewsModule_ProvideNewsRepositoryFactory(Providers.asDaggerProvider(apiServiceProvider), Providers.asDaggerProvider(newsDaoProvider));
  }

  public static NewsModule_ProvideNewsRepositoryFactory create(
      Provider<NewsApiService> apiServiceProvider, Provider<NewsDao> newsDaoProvider) {
    return new NewsModule_ProvideNewsRepositoryFactory(apiServiceProvider, newsDaoProvider);
  }

  public static NewsRepository provideNewsRepository(NewsApiService apiService, NewsDao newsDao) {
    return Preconditions.checkNotNullFromProvides(NewsModule.INSTANCE.provideNewsRepository(apiService, newsDao));
  }
}

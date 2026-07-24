package com.android.knewsapp.news.presentation.news_list;

import com.android.knewsapp.news.domain.repository.NewsRepository;
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
public final class NewsListViewModel_Factory implements Factory<NewsListViewModel> {
  private final Provider<NewsRepository> repositoryProvider;

  public NewsListViewModel_Factory(Provider<NewsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public NewsListViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static NewsListViewModel_Factory create(
      javax.inject.Provider<NewsRepository> repositoryProvider) {
    return new NewsListViewModel_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static NewsListViewModel_Factory create(Provider<NewsRepository> repositoryProvider) {
    return new NewsListViewModel_Factory(repositoryProvider);
  }

  public static NewsListViewModel newInstance(NewsRepository repository) {
    return new NewsListViewModel(repository);
  }
}

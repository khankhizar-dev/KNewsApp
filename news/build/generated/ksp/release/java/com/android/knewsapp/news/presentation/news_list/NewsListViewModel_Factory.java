package com.android.knewsapp.news.presentation.news_list;

import com.android.knewsapp.network.connectivity.ConnectivityObserver;
import com.android.knewsapp.news.domain.repository.NewsRepository;
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
public final class NewsListViewModel_Factory implements Factory<NewsListViewModel> {
  private final Provider<NewsRepository> repositoryProvider;

  private final Provider<ConnectivityObserver> connectivityObserverProvider;

  public NewsListViewModel_Factory(Provider<NewsRepository> repositoryProvider,
      Provider<ConnectivityObserver> connectivityObserverProvider) {
    this.repositoryProvider = repositoryProvider;
    this.connectivityObserverProvider = connectivityObserverProvider;
  }

  @Override
  public NewsListViewModel get() {
    return newInstance(repositoryProvider.get(), connectivityObserverProvider.get());
  }

  public static NewsListViewModel_Factory create(Provider<NewsRepository> repositoryProvider,
      Provider<ConnectivityObserver> connectivityObserverProvider) {
    return new NewsListViewModel_Factory(repositoryProvider, connectivityObserverProvider);
  }

  public static NewsListViewModel newInstance(NewsRepository repository,
      ConnectivityObserver connectivityObserver) {
    return new NewsListViewModel(repository, connectivityObserver);
  }
}

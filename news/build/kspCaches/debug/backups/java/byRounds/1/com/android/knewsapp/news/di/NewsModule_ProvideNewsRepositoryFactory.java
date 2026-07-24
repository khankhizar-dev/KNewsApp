package com.android.knewsapp.news.di;

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

  public NewsModule_ProvideNewsRepositoryFactory(Provider<NewsApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public NewsRepository get() {
    return provideNewsRepository(apiServiceProvider.get());
  }

  public static NewsModule_ProvideNewsRepositoryFactory create(
      javax.inject.Provider<NewsApiService> apiServiceProvider) {
    return new NewsModule_ProvideNewsRepositoryFactory(Providers.asDaggerProvider(apiServiceProvider));
  }

  public static NewsModule_ProvideNewsRepositoryFactory create(
      Provider<NewsApiService> apiServiceProvider) {
    return new NewsModule_ProvideNewsRepositoryFactory(apiServiceProvider);
  }

  public static NewsRepository provideNewsRepository(NewsApiService apiService) {
    return Preconditions.checkNotNullFromProvides(NewsModule.INSTANCE.provideNewsRepository(apiService));
  }
}

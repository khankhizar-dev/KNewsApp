package com.android.knewsapp.news.di;

import com.android.knewsapp.news.data.local.NewsDatabase;
import com.android.knewsapp.news.data.local.dao.NewsDao;
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
public final class NewsModule_ProvideNewsDaoFactory implements Factory<NewsDao> {
  private final Provider<NewsDatabase> databaseProvider;

  public NewsModule_ProvideNewsDaoFactory(Provider<NewsDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public NewsDao get() {
    return provideNewsDao(databaseProvider.get());
  }

  public static NewsModule_ProvideNewsDaoFactory create(
      javax.inject.Provider<NewsDatabase> databaseProvider) {
    return new NewsModule_ProvideNewsDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static NewsModule_ProvideNewsDaoFactory create(Provider<NewsDatabase> databaseProvider) {
    return new NewsModule_ProvideNewsDaoFactory(databaseProvider);
  }

  public static NewsDao provideNewsDao(NewsDatabase database) {
    return Preconditions.checkNotNullFromProvides(NewsModule.INSTANCE.provideNewsDao(database));
  }
}

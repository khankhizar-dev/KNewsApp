package com.android.knewsapp.news.di;

import android.content.Context;
import com.android.knewsapp.news.data.local.NewsDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class NewsModule_ProvideNewsDatabaseFactory implements Factory<NewsDatabase> {
  private final Provider<Context> contextProvider;

  public NewsModule_ProvideNewsDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NewsDatabase get() {
    return provideNewsDatabase(contextProvider.get());
  }

  public static NewsModule_ProvideNewsDatabaseFactory create(
      javax.inject.Provider<Context> contextProvider) {
    return new NewsModule_ProvideNewsDatabaseFactory(Providers.asDaggerProvider(contextProvider));
  }

  public static NewsModule_ProvideNewsDatabaseFactory create(Provider<Context> contextProvider) {
    return new NewsModule_ProvideNewsDatabaseFactory(contextProvider);
  }

  public static NewsDatabase provideNewsDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(NewsModule.INSTANCE.provideNewsDatabase(context));
  }
}

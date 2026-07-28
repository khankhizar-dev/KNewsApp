package com.android.knewsapp.news.di;

import com.android.knewsapp.news.data.remote.NewsApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
    "cast"
})
public final class NewsModule_ProvideNewsApiServiceFactory implements Factory<NewsApiService> {
  private final Provider<Retrofit.Builder> retrofitBuilderProvider;

  public NewsModule_ProvideNewsApiServiceFactory(
      Provider<Retrofit.Builder> retrofitBuilderProvider) {
    this.retrofitBuilderProvider = retrofitBuilderProvider;
  }

  @Override
  public NewsApiService get() {
    return provideNewsApiService(retrofitBuilderProvider.get());
  }

  public static NewsModule_ProvideNewsApiServiceFactory create(
      Provider<Retrofit.Builder> retrofitBuilderProvider) {
    return new NewsModule_ProvideNewsApiServiceFactory(retrofitBuilderProvider);
  }

  public static NewsApiService provideNewsApiService(Retrofit.Builder retrofitBuilder) {
    return Preconditions.checkNotNullFromProvides(NewsModule.INSTANCE.provideNewsApiService(retrofitBuilder));
  }
}

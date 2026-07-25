package com.android.knewsapp.network.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
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
public final class NetworkModule_ProvideRetrofitBuilderFactory implements Factory<Retrofit.Builder> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetworkModule_ProvideRetrofitBuilderFactory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public Retrofit.Builder get() {
    return provideRetrofitBuilder(okHttpClientProvider.get());
  }

  public static NetworkModule_ProvideRetrofitBuilderFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideRetrofitBuilderFactory(okHttpClientProvider);
  }

  public static Retrofit.Builder provideRetrofitBuilder(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRetrofitBuilder(okHttpClient));
  }
}

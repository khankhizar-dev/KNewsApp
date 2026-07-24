package com.android.knewsapp.network.interceptors;

import com.android.knewsapp.session.SessionManager;
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
public final class AuthInterceptor_Factory implements Factory<AuthInterceptor> {
  private final Provider<SessionManager> sessionManagerProvider;

  public AuthInterceptor_Factory(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public AuthInterceptor get() {
    return newInstance(sessionManagerProvider.get());
  }

  public static AuthInterceptor_Factory create(
      javax.inject.Provider<SessionManager> sessionManagerProvider) {
    return new AuthInterceptor_Factory(Providers.asDaggerProvider(sessionManagerProvider));
  }

  public static AuthInterceptor_Factory create(Provider<SessionManager> sessionManagerProvider) {
    return new AuthInterceptor_Factory(sessionManagerProvider);
  }

  public static AuthInterceptor newInstance(SessionManager sessionManager) {
    return new AuthInterceptor(sessionManager);
  }
}

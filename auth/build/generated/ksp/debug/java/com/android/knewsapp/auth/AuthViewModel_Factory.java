package com.android.knewsapp.auth;

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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<SessionManager> sessionManagerProvider;

  public AuthViewModel_Factory(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(sessionManagerProvider.get());
  }

  public static AuthViewModel_Factory create(
      javax.inject.Provider<SessionManager> sessionManagerProvider) {
    return new AuthViewModel_Factory(Providers.asDaggerProvider(sessionManagerProvider));
  }

  public static AuthViewModel_Factory create(Provider<SessionManager> sessionManagerProvider) {
    return new AuthViewModel_Factory(sessionManagerProvider);
  }

  public static AuthViewModel newInstance(SessionManager sessionManager) {
    return new AuthViewModel(sessionManager);
  }
}

package com.android.knewsapp.network.interceptors;

import com.android.knewsapp.security.SecurityManager;
import com.android.knewsapp.session.SessionManager;
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
public final class AuthInterceptor_Factory implements Factory<AuthInterceptor> {
  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<SecurityManager> securityManagerProvider;

  public AuthInterceptor_Factory(Provider<SessionManager> sessionManagerProvider,
      Provider<SecurityManager> securityManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
    this.securityManagerProvider = securityManagerProvider;
  }

  @Override
  public AuthInterceptor get() {
    return newInstance(sessionManagerProvider.get(), securityManagerProvider.get());
  }

  public static AuthInterceptor_Factory create(Provider<SessionManager> sessionManagerProvider,
      Provider<SecurityManager> securityManagerProvider) {
    return new AuthInterceptor_Factory(sessionManagerProvider, securityManagerProvider);
  }

  public static AuthInterceptor newInstance(SessionManager sessionManager,
      SecurityManager securityManager) {
    return new AuthInterceptor(sessionManager, securityManager);
  }
}

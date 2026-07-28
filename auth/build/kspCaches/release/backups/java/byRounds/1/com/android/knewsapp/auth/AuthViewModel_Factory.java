package com.android.knewsapp.auth;

import com.android.knewsapp.network.connectivity.ConnectivityObserver;
import com.android.knewsapp.session.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<FirebaseAuth> authProvider;

  private final Provider<ConnectivityObserver> connectivityObserverProvider;

  public AuthViewModel_Factory(Provider<SessionManager> sessionManagerProvider,
      Provider<FirebaseAuth> authProvider,
      Provider<ConnectivityObserver> connectivityObserverProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
    this.authProvider = authProvider;
    this.connectivityObserverProvider = connectivityObserverProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(sessionManagerProvider.get(), authProvider.get(), connectivityObserverProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<SessionManager> sessionManagerProvider,
      Provider<FirebaseAuth> authProvider,
      Provider<ConnectivityObserver> connectivityObserverProvider) {
    return new AuthViewModel_Factory(sessionManagerProvider, authProvider, connectivityObserverProvider);
  }

  public static AuthViewModel newInstance(SessionManager sessionManager, FirebaseAuth auth,
      ConnectivityObserver connectivityObserver) {
    return new AuthViewModel(sessionManager, auth, connectivityObserver);
  }
}

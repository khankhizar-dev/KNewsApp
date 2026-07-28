package com.android.knewsapp.session.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.android.knewsapp.session.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SessionModule_ProvideSessionManagerFactory implements Factory<SessionManager> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  private final Provider<FirebaseAuth> authProvider;

  public SessionModule_ProvideSessionManagerFactory(
      Provider<DataStore<Preferences>> dataStoreProvider, Provider<FirebaseAuth> authProvider) {
    this.dataStoreProvider = dataStoreProvider;
    this.authProvider = authProvider;
  }

  @Override
  public SessionManager get() {
    return provideSessionManager(dataStoreProvider.get(), authProvider.get());
  }

  public static SessionModule_ProvideSessionManagerFactory create(
      Provider<DataStore<Preferences>> dataStoreProvider, Provider<FirebaseAuth> authProvider) {
    return new SessionModule_ProvideSessionManagerFactory(dataStoreProvider, authProvider);
  }

  public static SessionManager provideSessionManager(DataStore<Preferences> dataStore,
      FirebaseAuth auth) {
    return Preconditions.checkNotNullFromProvides(SessionModule.INSTANCE.provideSessionManager(dataStore, auth));
  }
}

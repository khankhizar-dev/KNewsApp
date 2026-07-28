package com.android.knewsapp.session;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
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
public final class SessionManager_Factory implements Factory<SessionManager> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  private final Provider<FirebaseAuth> authProvider;

  public SessionManager_Factory(Provider<DataStore<Preferences>> dataStoreProvider,
      Provider<FirebaseAuth> authProvider) {
    this.dataStoreProvider = dataStoreProvider;
    this.authProvider = authProvider;
  }

  @Override
  public SessionManager get() {
    return newInstance(dataStoreProvider.get(), authProvider.get());
  }

  public static SessionManager_Factory create(Provider<DataStore<Preferences>> dataStoreProvider,
      Provider<FirebaseAuth> authProvider) {
    return new SessionManager_Factory(dataStoreProvider, authProvider);
  }

  public static SessionManager newInstance(DataStore<Preferences> dataStore, FirebaseAuth auth) {
    return new SessionManager(dataStore, auth);
  }
}

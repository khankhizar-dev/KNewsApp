package com.android.knewsapp.session.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.android.knewsapp.session.SessionManager;
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
public final class SessionModule_ProvideSessionManagerFactory implements Factory<SessionManager> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public SessionModule_ProvideSessionManagerFactory(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public SessionManager get() {
    return provideSessionManager(dataStoreProvider.get());
  }

  public static SessionModule_ProvideSessionManagerFactory create(
      javax.inject.Provider<DataStore<Preferences>> dataStoreProvider) {
    return new SessionModule_ProvideSessionManagerFactory(Providers.asDaggerProvider(dataStoreProvider));
  }

  public static SessionModule_ProvideSessionManagerFactory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new SessionModule_ProvideSessionManagerFactory(dataStoreProvider);
  }

  public static SessionManager provideSessionManager(DataStore<Preferences> dataStore) {
    return Preconditions.checkNotNullFromProvides(SessionModule.INSTANCE.provideSessionManager(dataStore));
  }
}

package com.android.knewsapp.session.di;

import android.content.Context;
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
public final class SessionModule_ProvideSessionManagerFactory implements Factory<SessionManager> {
  private final Provider<Context> contextProvider;

  public SessionModule_ProvideSessionManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SessionManager get() {
    return provideSessionManager(contextProvider.get());
  }

  public static SessionModule_ProvideSessionManagerFactory create(
      javax.inject.Provider<Context> contextProvider) {
    return new SessionModule_ProvideSessionManagerFactory(Providers.asDaggerProvider(contextProvider));
  }

  public static SessionModule_ProvideSessionManagerFactory create(
      Provider<Context> contextProvider) {
    return new SessionModule_ProvideSessionManagerFactory(contextProvider);
  }

  public static SessionManager provideSessionManager(Context context) {
    return Preconditions.checkNotNullFromProvides(SessionModule.INSTANCE.provideSessionManager(context));
  }
}

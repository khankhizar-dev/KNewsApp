package com.android.knewsapp.security;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
    "cast"
})
public final class KeyManager_Factory implements Factory<KeyManager> {
  @Override
  public KeyManager get() {
    return newInstance();
  }

  public static KeyManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static KeyManager newInstance() {
    return new KeyManager();
  }

  private static final class InstanceHolder {
    private static final KeyManager_Factory INSTANCE = new KeyManager_Factory();
  }
}

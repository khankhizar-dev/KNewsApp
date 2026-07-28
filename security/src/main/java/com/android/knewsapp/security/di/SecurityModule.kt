package com.android.knewsapp.security.di

import com.android.knewsapp.security.KeyManager
import com.android.knewsapp.security.SecurityManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {
    @Binds
    @Singleton
    abstract fun bindSecurityManager(keyManager: KeyManager): SecurityManager
}

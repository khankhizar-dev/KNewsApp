package com.android.knewsapp.network.interceptors

import com.android.knewsapp.security.SecurityManager
import com.android.knewsapp.session.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor
    @Inject
    constructor(
        private val sessionManager: SessionManager,
        private val securityManager: SecurityManager,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            // Get JWT from SessionManager (running blocking for simplicity in Interceptor)
            val token = runBlocking { sessionManager.userEmail.first() } // For now using userEmail as proxy for token

            val requestBuilder = originalRequest.newBuilder()

            if (token != null) {
                requestBuilder.addHeader("Authorization", "Bearer $token")

                // Secure Request Signing with EC Keys
                val timestamp = System.currentTimeMillis().toString()
                val challenge = originalRequest.url.toString() + timestamp
                val signature = securityManager.signData(challenge)

                requestBuilder.addHeader("X-KNews-Signature", signature)
                requestBuilder.addHeader("X-KNews-Timestamp", timestamp)
                requestBuilder.addHeader("X-KNews-PublicKey", securityManager.getPublicKey())
            }

            return chain.proceed(requestBuilder.build())
        }
    }

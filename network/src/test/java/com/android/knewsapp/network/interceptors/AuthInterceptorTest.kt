package com.android.knewsapp.network.interceptors

import com.android.knewsapp.session.SessionManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class AuthInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sessionManager: SessionManager
    private lateinit var authInterceptor: AuthInterceptor
    private lateinit var client: OkHttpClient

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        sessionManager = mockk()
        authInterceptor = AuthInterceptor(sessionManager)
        client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `intercept adds Authorization header when token exists`() {
        val testToken = "test-jwt-token"
        every { sessionManager.userEmail } returns flowOf(testToken)
        
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()

        client.newCall(request).execute()

        val recordedRequest = mockWebServer.takeRequest()
        assertThat(recordedRequest.getHeader("Authorization")).isEqualTo("Bearer $testToken")
    }

    @Test
    fun `intercept adds security headers when token exists`() {
        every { sessionManager.userEmail } returns flowOf("some-token")
        
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()

        client.newCall(request).execute()

        val recordedRequest = mockWebServer.takeRequest()
        assertThat(recordedRequest.getHeader("X-KNews-Signature")).isNotNull()
        assertThat(recordedRequest.getHeader("X-KNews-Timestamp")).isNotNull()
        assertThat(recordedRequest.getHeader("X-KNews-PublicKey")).isNotNull()
    }
}

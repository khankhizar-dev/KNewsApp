package com.android.knewsapp.auth

import app.cash.turbine.test
import com.android.knewsapp.session.SessionManager
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: AuthViewModel
    private val sessionManager: SessionManager = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { sessionManager.user } returns MutableStateFlow(null)
        viewModel = AuthViewModel(sessionManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial loading state is checking session`() = runTest {
        viewModel.isCheckingSession.test {
            // First item should be true (initially checking)
            assertThat(awaitItem()).isTrue()
            // Then it should become false after checkSession completes
            assertThat(awaitItem()).isFalse()
        }
    }
}

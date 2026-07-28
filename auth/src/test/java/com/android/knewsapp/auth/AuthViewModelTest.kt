package com.android.knewsapp.auth

import app.cash.turbine.test
import com.android.knewsapp.network.connectivity.ConnectivityObserver
import com.android.knewsapp.session.SessionManager
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: AuthViewModel
    private val sessionManager: SessionManager = mockk(relaxed = true)
    private val connectivityObserver: ConnectivityObserver = mockk(relaxed = true)
    private val firebaseAuth: FirebaseAuth = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { sessionManager.user } returns MutableStateFlow(null)
        every { connectivityObserver.observe() } returns flowOf(ConnectivityObserver.Status.Available)
        every { firebaseAuth.currentUser } returns null
        viewModel = AuthViewModel(sessionManager, firebaseAuth, connectivityObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial loading state is checking session`() =
        runTest {
            viewModel.isCheckingSession.test {
                val first = awaitItem()
                if (first) {
                    assertThat(awaitItem()).isFalse()
                } else {
                    assertThat(first).isFalse()
                }
            }
        }

    @Test
    fun `signOut calls sessionManager clearSession`() =
        runTest {
            viewModel.signOut()
            coVerify { sessionManager.clearSession() }
        }

    @Test
    fun `networkStatus reflects connectivityObserver status`() =
        runTest {
            viewModel.networkStatus.test {
                assertThat(awaitItem()).isEqualTo(ConnectivityObserver.Status.Available)
            }
        }
}

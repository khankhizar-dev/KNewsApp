package com.android.knewsapp.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NetworkConnectivityObserverTest {
    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var observer: NetworkConnectivityObserver

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        connectivityManager = mockk(relaxed = true)
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        observer = NetworkConnectivityObserver(context)
    }

    @Test
    fun `observe emits initial and subsequent status changes`() =
        runTest {
            val callbackSlot = mutableListOf<ConnectivityManager.NetworkCallback>()
            every {
                connectivityManager.registerDefaultNetworkCallback(capture(callbackSlot))
            } returns Unit

            observer.observe().test {
                val callback = callbackSlot.first()

                callback.onAvailable(mockk())
                assert(awaitItem() == ConnectivityObserver.Status.Available)

                callback.onLost(mockk())
                assert(awaitItem() == ConnectivityObserver.Status.Lost)

                val maxMsToLive = 100
                callback.onLosing(mockk(), maxMsToLive)
                assert(awaitItem() == ConnectivityObserver.Status.Losing)

                callback.onUnavailable()
                assert(awaitItem() == ConnectivityObserver.Status.Unavailable)
            }

            verify { connectivityManager.unregisterNetworkCallback(any<ConnectivityManager.NetworkCallback>()) }
        }
}

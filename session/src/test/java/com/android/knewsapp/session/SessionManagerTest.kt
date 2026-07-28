package com.android.knewsapp.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class SessionManagerTest {
    @get:Rule
    val tmpFolder = TemporaryFolder()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var sessionManager: SessionManager
    private lateinit var dataStore: DataStore<Preferences>
    private val firebaseAuth: FirebaseAuth = mockk(relaxed = true)

    @Before
    fun setup() {
        dataStore =
            PreferenceDataStoreFactory.create(
                scope = testScope,
                produceFile = { tmpFolder.newFile("test.preferences_pb") },
            )
        every { firebaseAuth.currentUser } returns null
        sessionManager = SessionManager(dataStore, firebaseAuth)
    }

    @Test
    fun `userEmail initial state is null`() =
        runTest {
            val email = sessionManager.userEmail.first()
            assertThat(email).isNull()
        }

    @Test
    fun `userEmail returns correct value after manual datastore edit`() =
        runTest {
            val testEmail = "test@example.com"
            dataStore.edit { prefs ->
                prefs[SessionManager.USER_EMAIL] = testEmail
            }

            val email = sessionManager.userEmail.first()
            assertThat(email).isEqualTo(testEmail)
        }

    @Test
    fun `clearSession clears the datastore`() =
        runTest {
            dataStore.edit { prefs ->
                prefs[SessionManager.USER_EMAIL] = "test@example.com"
                prefs[SessionManager.USER_ID] = "123"
            }

            sessionManager.clearSession()

            val email = sessionManager.userEmail.first()
            assertThat(email).isNull()
        }
}

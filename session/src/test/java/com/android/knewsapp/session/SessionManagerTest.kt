package com.android.knewsapp.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.common.truth.Truth.assertThat
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

    @Before
    fun setup() {
        dataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tmpFolder.newFile("test.preferences_pb") }
        )
        sessionManager = SessionManager(dataStore)
    }

    @Test
    fun `userEmail initial state is null`() = testScope.runTest {
        val email = sessionManager.userEmail.first()
        assertThat(email).isNull()
    }

    @Test
    fun `userEmail returns correct value after update`() = testScope.runTest {
        val testEmail = "test@example.com"
        dataStore.edit { prefs ->
            prefs[SessionManager.USER_EMAIL] = testEmail
        }
        
        val email = sessionManager.userEmail.first()
        assertThat(email).isEqualTo(testEmail)
    }
}

package com.android.knewsapp.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val auth = FirebaseAuth.getInstance()

    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    companion object {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_ID = stringPreferencesKey("user_id")
        val LAST_LOGIN_UID = stringPreferencesKey("last_login_uid")
    }

    val userEmail: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_EMAIL]
    }

    suspend fun updateSession() {
        val currentUser = auth.currentUser
        _user.value = currentUser
        currentUser?.let { user ->
            dataStore.edit { preferences ->
                preferences[USER_EMAIL] = user.email ?: ""
                preferences[USER_ID] = user.uid
                preferences[LAST_LOGIN_UID] = user.uid
            }
        }
    }

    suspend fun clearSession() {
        auth.signOut()
        _user.value = null
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

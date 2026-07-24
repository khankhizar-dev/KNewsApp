package com.android.knewsapp.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

class SessionManager(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()

    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    companion object {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_ID = stringPreferencesKey("user_id")
        val LAST_LOGIN_UID = stringPreferencesKey("last_login_uid")
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL]
    }

    suspend fun updateSession() {
        val currentUser = auth.currentUser
        _user.value = currentUser
        currentUser?.let { user ->
            context.dataStore.edit { preferences ->
                preferences[USER_EMAIL] = user.email ?: ""
                preferences[USER_ID] = user.uid
                preferences[LAST_LOGIN_UID] = user.uid
            }
        }
    }

    suspend fun clearSession() {
        auth.signOut()
        _user.value = null
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

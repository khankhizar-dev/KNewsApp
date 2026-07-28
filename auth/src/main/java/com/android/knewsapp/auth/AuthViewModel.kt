package com.android.knewsapp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.knewsapp.network.connectivity.ConnectivityObserver
import com.android.knewsapp.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
        private val auth: FirebaseAuth,
        connectivityObserver: ConnectivityObserver,
    ) : ViewModel() {
        val user: StateFlow<FirebaseUser?> = sessionManager.user

        val networkStatus: StateFlow<ConnectivityObserver.Status> =
            connectivityObserver.observe()
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    ConnectivityObserver.Status.Unavailable,
                )

        private val _loading = MutableStateFlow(false)
        val loading: StateFlow<Boolean> = _loading

        private val _error = MutableStateFlow<String?>(null)
        val error: StateFlow<String?> = _error

        private val _isCheckingSession = MutableStateFlow(true)
        val isCheckingSession: StateFlow<Boolean> = _isCheckingSession

        init {
            checkSession()
        }

        private fun checkSession() {
            viewModelScope.launch {
                // Give Firebase a moment to restore the session if needed
                if (auth.currentUser != null) {
                    sessionManager.updateSession()
                }
                _isCheckingSession.value = false
            }
        }

        fun resetInactivityTimer() {
            // Removed aggressive 3-minute auto-logout to ensure persistent session
            // Firebase handles token refresh automatically.
        }

        fun signInWithEmail(
            email: String,
            password: String,
            onSuccess: () -> Unit,
        ) {
            viewModelScope.launch {
                _loading.value = true
                _error.value = null
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    sessionManager.updateSession()
                    onSuccess()
                } catch (e: Exception) {
                    _error.value = e.message ?: "Sign in failed"
                } finally {
                    _loading.value = false
                }
            }
        }

        fun signUpWithEmail(
            email: String,
            password: String,
            onSuccess: () -> Unit,
        ) {
            viewModelScope.launch {
                _loading.value = true
                _error.value = null
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    sessionManager.updateSession()
                    onSuccess()
                } catch (e: Exception) {
                    _error.value = e.message ?: "Sign up failed"
                } finally {
                    _loading.value = false
                }
            }
        }

        fun signInWithGoogle(
            idToken: String,
            onSuccess: () -> Unit,
        ) {
            viewModelScope.launch {
                _loading.value = true
                _error.value = null
                try {
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(credential).await()
                    sessionManager.updateSession()
                    onSuccess()
                } catch (e: Exception) {
                    _error.value = e.message ?: "Google sign in failed"
                } finally {
                    _loading.value = false
                }
            }
        }

        fun signOut() {
            viewModelScope.launch {
                sessionManager.clearSession()
            }
        }
    }

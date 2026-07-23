package com.android.knewsapp.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.knewsapp.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()
    private val sessionManager = SessionManager(application)

    val user: StateFlow<FirebaseUser?> = sessionManager.user

    private var inactivityJob: Job? = null
    private val INACTIVITY_TIMEOUT = 3 * 60 * 1000L // 3 minutes

    init {
        // Start monitoring user session to handle timeout
        viewModelScope.launch {
            user.collect { firebaseUser ->
                if (firebaseUser != null) {
                    resetInactivityTimer()
                } else {
                    inactivityJob?.cancel()
                }
            }
        }
    }

    fun resetInactivityTimer() {
        inactivityJob?.cancel()
        if (auth.currentUser != null) {
            // Optional: Refresh token to ensure session is valid
            auth.currentUser?.getIdToken(false) 

            inactivityJob = viewModelScope.launch {
                delay(INACTIVITY_TIMEOUT)
                signOut()
            }
        }
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun signInWithEmail(email: String, password: String, onSuccess: () -> Unit) {
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

    fun signUpWithEmail(email: String, password: String, onSuccess: () -> Unit) {
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

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit) {
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

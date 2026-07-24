package com.android.knewsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.knewsapp.auth.AuthViewModel
import com.android.knewsapp.auth.LoginScreen
import com.android.knewsapp.auth.SignUpScreen
import com.android.knewsapp.core_ui.theme.Dimensions
import com.android.knewsapp.core_ui.theme.KNewsAppTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onUserInteraction() {
        super.onUserInteraction()
        authViewModel.resetInactivityTimer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KNewsAppTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()
                val user by authViewModel.user.collectAsStateWithLifecycle()
                val scope = rememberCoroutineScope()
                val credentialManager = CredentialManager.create(this)

                LaunchedEffect(user) {
                    if (user == null) {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                fun launchGoogleSignIn() {
                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setAutoSelectEnabled(true)
                        .build()

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build()

                    scope.launch {
                        try {
                            val result = credentialManager.getCredential(
                                context = this@MainActivity,
                                request = request
                            )
                            val credential = result.credential
                            if (credential is GoogleIdTokenCredential) {
                                authViewModel.signInWithGoogle(credential.idToken) {
                                    navController.navigate("main") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            }
                        } catch (e: GetCredentialException) {
                            Toast.makeText(this@MainActivity, "Sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = if (user != null) "main" else "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            viewModel = authViewModel,
                            onNavigateToSignUp = { navController.navigate("signup") },
                            onLoginSuccess = {
                                navController.navigate("main") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onGoogleSignInClick = ::launchGoogleSignIn
                        )
                    }
                    composable("signup") {
                        SignUpScreen(
                            viewModel = authViewModel,
                            onNavigateToLogin = { navController.navigate("login") },
                            onSignUpSuccess = {
                                navController.navigate("main") {
                                    popUpTo("signup") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("main") {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            Greeting(
                                name = user?.email ?: "User",
                                onSignOut = {
                                    authViewModel.signOut()
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, onSignOut: () -> Unit, modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome $name!")
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))
        androidx.compose.material3.Button(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}

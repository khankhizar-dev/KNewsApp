package com.android.knewsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.android.knewsapp.news.presentation.news_detail.NewsDetailScreen
import com.android.knewsapp.news.presentation.news_list.NewsListScreen
import com.android.knewsapp.news.presentation.news_list.NewsListViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            val context = baseContext
            val credentialManager = CredentialManager.create(context)
            val authViewModel: AuthViewModel = hiltViewModel()

            KNewsAppTheme {
                val navController = rememberNavController()
                val user by authViewModel.user.collectAsStateWithLifecycle()
                val isCheckingSession by authViewModel.isCheckingSession.collectAsStateWithLifecycle()

                if (isCheckingSession) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    LaunchedEffect(user) {
                        if (user != null) {
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }

                    NavHost(navController = navController, startDestination = if (user != null) "main" else "login") {
                        composable("login") {
                            LoginScreen(
                                viewModel = authViewModel,
                                onNavigateToSignUp = {
                                    navController.navigate("signup")
                                },
                                onLoginSuccess = {
                                    // Handled by LaunchedEffect
                                },
                                onGoogleSignInClick = {
                                    scope.launch {
                                        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                                            .setFilterByAuthorizedAccounts(false)
                                            .setServerClientId(getString(R.string.default_web_client_id))
                                            .build()

                                        val request: GetCredentialRequest = GetCredentialRequest.Builder()
                                            .addCredentialOption(googleIdOption)
                                            .build()

                                        try {
                                            val result = credentialManager.getCredential(
                                                context = this@MainActivity,
                                                request = request,
                                            )
                                            val credential = result.credential
                                            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                                            val idToken = googleIdTokenCredential.idToken
                                            
                                            authViewModel.signInWithGoogle(idToken) {
                                                // Success handled by LaunchedEffect
                                            }
                                        } catch (e: GetCredentialException) {
                                            Toast.makeText(context, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            )
                        }
                        composable("signup") {
                            SignUpScreen(
                                viewModel = authViewModel,
                                onNavigateToLogin = {
                                    navController.popBackStack()
                                },
                                onSignUpSuccess = {
                                    // Handled by LaunchedEffect
                                }
                            )
                        }
                        composable("main") {
                            val newsViewModel: NewsListViewModel = hiltViewModel()
                            NewsListScreen(
                                viewModel = newsViewModel,
                                onArticleClick = { article ->
                                    newsViewModel.selectArticle(article)
                                    navController.navigate("detail")
                                },
                                onLogoutClick = {
                                    authViewModel.signOut()
                                }
                            )
                        }

                        composable("detail") {
                            val parentEntry = remember(it) {
                                navController.getBackStackEntry("main")
                            }
                            val newsViewModel: NewsListViewModel = hiltViewModel(parentEntry)
                            val article by newsViewModel.selectedArticle.collectAsStateWithLifecycle()
                            val fullStory by newsViewModel.fullContent.collectAsStateWithLifecycle()
                            
                            article?.let {
                                NewsDetailScreen(
                                    article = it,
                                    fullStory = fullStory,
                                    onBackClick = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        // Reset inactivity timer on any user interaction
        val authViewModel: AuthViewModel by viewModels()
        authViewModel.resetInactivityTimer()
    }
}

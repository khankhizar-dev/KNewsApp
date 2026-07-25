package com.android.knewsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import com.android.knewsapp.auth.ProfileScreen
import com.android.knewsapp.auth.SignUpScreen
import com.android.knewsapp.core_ui.theme.KNewsAppTheme
import com.android.knewsapp.network.connectivity.ConnectivityObserver
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
                val networkStatus by authViewModel.networkStatus.collectAsStateWithLifecycle()

                Column(modifier = Modifier.fillMaxSize()) {
                    AnimatedVisibility(
                        visible = networkStatus != ConnectivityObserver.Status.Available,
                        enter = expandVertically(),
                        exit = shrinkVertically(),
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .background(Color.Red.copy(alpha = 0.8f))
                                    .padding(8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(R.string.no_internet_connection),
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                    if (isCheckingSession) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        LaunchedEffect(user) {
                            if (user != null) {
                                // Only navigate to main if we are on login/signup
                                val currentRoute = navController.currentDestination?.route
                                if (currentRoute == "login" || currentRoute == "signup" || currentRoute == null) {
                                    navController.navigate("main") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            } else {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }

                        NavHost(
                            navController = navController,
                            startDestination = if (user != null) "main" else "login",
                            modifier = Modifier.weight(1f),
                        ) {
                            composable("login") {
                                LoginScreen(
                                    viewModel = authViewModel,
                                    onNavigateToSignUp = {
                                        navController.navigate("signup")
                                    },
                                    onLoginSuccess = {},
                                    onGoogleSignInClick = {
                                        scope.launch {
                                            val googleIdOption: GetGoogleIdOption =
                                                GetGoogleIdOption.Builder()
                                                    .setFilterByAuthorizedAccounts(false)
                                                    .setServerClientId(getString(R.string.default_web_client_id))
                                                    .build()

                                            val request: GetCredentialRequest =
                                                GetCredentialRequest.Builder()
                                                    .addCredentialOption(googleIdOption)
                                                    .build()

                                            try {
                                                val result =
                                                    credentialManager.getCredential(
                                                        context = this@MainActivity,
                                                        request = request,
                                                    )
                                                val credential = result.credential
                                                val googleIdTokenCredential =
                                                    GoogleIdTokenCredential.createFrom(credential.data)
                                                val idToken = googleIdTokenCredential.idToken

                                                authViewModel.signInWithGoogle(idToken) {}
                                            } catch (e: GetCredentialException) {
                                                val msg = getString(R.string.google_sign_in_failed, e.message)
                                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    },
                                )
                            }
                            composable("signup") {
                                SignUpScreen(
                                    viewModel = authViewModel,
                                    onNavigateToLogin = {
                                        navController.popBackStack()
                                    },
                                    onSignUpSuccess = {},
                                )
                            }
                            composable("main") {
                                var bottomTab by remember { mutableIntStateOf(0) }
                                val newsViewModel: NewsListViewModel = hiltViewModel()

                                Scaffold(
                                    bottomBar = {
                                        NavigationBar {
                                            NavigationBarItem(
                                                selected = bottomTab == 0,
                                                onClick = { bottomTab = 0 },
                                                icon = {
                                                    Icon(
                                                        imageVector = Icons.Default.Home,
                                                        contentDescription = stringResource(R.string.news),
                                                    )
                                                },
                                                label = { Text(stringResource(R.string.news)) },
                                            )
                                            NavigationBarItem(
                                                selected = bottomTab == 1,
                                                onClick = { bottomTab = 1 },
                                                icon = {
                                                    Icon(
                                                        imageVector = Icons.Default.Person,
                                                        contentDescription = stringResource(R.string.profile),
                                                    )
                                                },
                                                label = { Text(stringResource(R.string.profile)) },
                                            )
                                        }
                                    },
                                ) { innerPadding ->
                                    Box(modifier = Modifier.padding(innerPadding)) {
                                        if (bottomTab == 0) {
                                            NewsListScreen(
                                                viewModel = newsViewModel,
                                                onArticleClick = { article ->
                                                    newsViewModel.selectArticle(article)
                                                    navController.navigate("detail")
                                                },
                                            )
                                        } else {
                                            ProfileScreen(
                                                viewModel = authViewModel,
                                                onLogoutClick = {
                                                    authViewModel.signOut()
                                                },
                                            )
                                        }
                                    }
                                }
                            }

                            composable("detail") {
                                val parentEntry =
                                    remember(it) {
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
                                        },
                                        onBookmarkClick = {
                                            newsViewModel.toggleBookmark(it)
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        val authViewModel: AuthViewModel by viewModels()
        authViewModel.resetInactivityTimer()
    }
}

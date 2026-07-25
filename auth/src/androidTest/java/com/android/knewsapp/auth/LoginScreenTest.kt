package com.android.knewsapp.auth

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.android.knewsapp.session.SessionManager
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val sessionManager: SessionManager = mockk(relaxed = true)

    @Test
    fun loginScreen_displaysAllFields() {
        every { sessionManager.user } returns MutableStateFlow(null)
        val viewModel = AuthViewModel(sessionManager)

        composeTestRule.setContent {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToSignUp = {},
                onLoginSuccess = {},
                onGoogleSignInClick = {},
            )
        }

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign in with Google").assertIsDisplayed()
    }
}

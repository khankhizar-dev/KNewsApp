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

class SignUpScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val sessionManager: SessionManager = mockk(relaxed = true)

    @Test
    fun signUpScreen_displaysAllFields() {
        every { sessionManager.user } returns MutableStateFlow(null)
        val viewModel = AuthViewModel(sessionManager)

        composeTestRule.setContent {
            SignUpScreen(
                viewModel = viewModel,
                onNavigateToLogin = {},
                onSignUpSuccess = {},
            )
        }

        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Already have an account? Login").assertIsDisplayed()
    }
}

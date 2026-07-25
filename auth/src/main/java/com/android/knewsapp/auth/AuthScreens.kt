package com.android.knewsapp.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.knewsapp.core_ui.components.KNewsButton
import com.android.knewsapp.core_ui.theme.Dimensions

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by viewModel.error.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(Dimensions.PaddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.login), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))
        }

        KNewsButton(
            text = stringResource(R.string.login),
            onClick = { viewModel.signInWithEmail(email, password, onLoginSuccess) },
            isLoading = loading,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))

        OutlinedButton(
            onClick = onGoogleSignInClick,
            enabled = !loading,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.sign_in_with_google))
        }

        TextButton(onClick = onNavigateToSignUp) {
            Text(stringResource(R.string.dont_have_account_signup))
        }
    }
}

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error by viewModel.error.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(Dimensions.PaddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.sign_up), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))
        }

        KNewsButton(
            text = stringResource(R.string.sign_up),
            onClick = { viewModel.signUpWithEmail(email, password, onSignUpSuccess) },
            isLoading = loading,
            modifier = Modifier.fillMaxWidth(),
        )

        TextButton(onClick = onNavigateToLogin) {
            Text(stringResource(R.string.already_have_account_login))
        }
    }
}

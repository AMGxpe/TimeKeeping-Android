package com.axpe.timekeeping.ui.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.axpe.timekeeping.R

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    navigateToHome: () -> Unit
) {
    val state by viewModel.loginUiState.collectAsStateWithLifecycle()
    when (state) {
        LoginViewModel.LoginUiState.Idle -> {
            Text("Loading")
        }

        LoginViewModel.LoginUiState.Loading -> {
            Text("Loading")
        }

        is LoginViewModel.LoginUiState.Success -> {
            navigateToHome()
        }

        is LoginViewModel.LoginUiState.Error -> {}
    }
    LoginScreen(modifier, login = viewModel::doLogin)
}


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    login: (username: String, password: String) -> Unit
) {
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(stringResource(R.string.login), style = MaterialTheme.typography.displayMedium)
        Column {
            TextField(
                value = username,
                onValueChange = setUsername,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.username)) })
            Spacer(Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = setPassword,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.password)) })
        }
        Spacer(Modifier.height(64.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { login(username, password) }) {
            Text(stringResource(R.string.login))
        }
    }

}
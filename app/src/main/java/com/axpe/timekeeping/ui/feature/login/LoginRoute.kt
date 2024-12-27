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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.axpe.timekeeping.R
import com.axpe.timekeeping.clearUserSession
import com.axpe.timekeeping.core.model.UserData
import com.axpe.timekeeping.getDataStoreUser
import com.axpe.timekeeping.setDataStoreUserId
import com.axpe.timekeeping.setDataStoreUsername
import com.axpe.timekeeping.setLogged
import com.axpe.timekeeping.ui.shared.loading.LoadingButton
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val state by viewModel.loginUiState.collectAsStateWithLifecycle()
    val userData by context.getDataStoreUser()
        .collectAsStateWithLifecycle(UserData.notLogged())
    LaunchedEffect(state.logged) {
        if (state.logged) {
            if (state.data != null) {
                coroutineScope.launch {
                    context.setDataStoreUsername(state.data!!.fullName)
                    context.setDataStoreUserId(state.data!!.employee)
                    context.setLogged()
                }
            }
            navigateToHome()
        }
    }
    when (userData.isLogged) {
        true -> {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Logged as ${userData.username}")
                Button(onClick = { viewModel.next() }) {
                    Text("Continue")
                }
                Button(onClick = { coroutineScope.launch { context.clearUserSession() } }) {
                    Text("Acceder con otra cuenta")
                }

            }
        }

        false ->
            Column {
                LoginScreen(
                    modifier = modifier,
                    isLoading = state.isLoading,
                    error = state.error,
                    login = viewModel::doLogin
                )
            }

    }

}


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    error: String? = null,
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
        if (error != null) {
            Text(error)
        }
        Spacer(Modifier.height(64.dp))
        LoadingButton(
            loading = isLoading,
            modifier = Modifier.fillMaxWidth(),

            onClick = { login(username, password) }) {
            Text(stringResource(R.string.login))
        }
    }

}
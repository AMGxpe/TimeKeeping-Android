package com.axpe.timekeeping.ui.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axpe.timekeeping.core.TimeKeepingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    val timeKeepingRepository = TimeKeepingRepository()


    fun doLogin(username: String, password: String) {
        _loginUiState.value = LoginUiState.Loading
        viewModelScope.launch {
            val response = timeKeepingRepository.doLogin(username, password)
            Log.d("AMG", "response ==> $response")
            delay(900)
            _loginUiState.value = LoginUiState.Success(username)
        }
    }


    sealed interface LoginUiState {
        data object Idle : LoginUiState
        data object Loading : LoginUiState
        data class Success(val email: String) : LoginUiState
        data class Error(val message: String) : LoginUiState

    }
}
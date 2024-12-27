package com.axpe.timekeeping.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axpe.timekeeping.core.PreferencesDataSource
import com.axpe.timekeeping.core.TimeKeepingRepository
import com.axpe.timekeeping.core.model.NetworkLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val timeKeepingRepository: TimeKeepingRepository,
    private val preferencesDataSource: PreferencesDataSource
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    val userData = preferencesDataSource.getDataStoreUser()
    fun next() {
        _loginUiState.update { it.copy(isLoading = false, logged = true, error = null) }
    }

    fun doLogin(username: String, password: String) {
        _loginUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val response = timeKeepingRepository.doLogin(username, password)
                delay(900)
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        logged = true,
                        error = null,
                        data = response
                    )
                }
            } catch (e: Exception) {
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        logged = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun setDataStoreUsername(fullName: String) {
        viewModelScope.launch {
            preferencesDataSource.setDataStoreUsername(fullName)
        }
    }

    fun setDataStoreUserId(employee: Long) {
        viewModelScope.launch {
            preferencesDataSource.setDataStoreUserId(employee)
        }
    }

    fun setLogged() {
        viewModelScope.launch {
            preferencesDataSource.setLogged()
        }
    }

    fun clearUserSession() {
        viewModelScope.launch {
            preferencesDataSource.clearUserSession()
        }
    }


    data class LoginUiState(
        val isLoading: Boolean = false,
        val logged: Boolean = false,
        val error: String? = null,
        val data: NetworkLogin? = null
    )

}
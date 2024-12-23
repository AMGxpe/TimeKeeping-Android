package com.axpe.timekeeping

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.axpe.timekeeping.core.model.UserData
import com.axpe.timekeeping.ui.feature.home.navigation.navigateToHome
import com.axpe.timekeeping.ui.feature.home.navigation.screenHome
import com.axpe.timekeeping.ui.feature.login.navigation.LoginRoute
import com.axpe.timekeeping.ui.feature.login.navigation.screenLogin
import com.axpe.timekeeping.ui.theme.TimeKeepingTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user")


private val usernameKey = stringPreferencesKey("username")
private val userIdKey = longPreferencesKey("userId")
private val loggedKey = booleanPreferencesKey("logged")

fun Context.getDataStoreUser(): Flow<UserData> {
    return dataStore.data.map { user ->
        val username = user[usernameKey]
        val userId = user[userIdKey]
        val logged = user[loggedKey] ?: false
        if (username == null || userId == null) {
            return@map UserData.notLogged()
        }
        UserData(
            username = username,
            userId = userId,
            isLogged = logged
        )
    }
}

fun Context.getDataStoreUserId(): Flow<Long> {
    return dataStore.data.map { user ->
        user[longPreferencesKey("userId")] ?: 0L
    }
}

suspend fun Context.clearUserSession() {
    dataStore.edit { user ->
        user.clear()
    }
}

suspend fun Context.setDataStoreUsername(username: String) {
    dataStore.edit { user ->
        user[usernameKey] = username
    }
}

suspend fun Context.setDataStoreUserId(userId: Long) {
    dataStore.edit { user ->
        user[userIdKey] = userId
    }
}

suspend fun Context.setLogged() {
    dataStore.edit { user ->
        user[loggedKey] = true
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeKeepingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        screenLogin {
                            navController.navigateToHome {
                                popUpTo(LoginRoute) {
                                    inclusive = true
                                }
                            }
                        }
                        screenHome()
                    }
                }

            }
        }
    }
}
package com.axpe.timekeeping.ui.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.axpe.timekeeping.ui.feature.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}
) = navigate(route = LoginRoute, navOptionsBuilder)

fun NavGraphBuilder.screenLogin(onLogin: () -> Unit) {
    composable<LoginRoute> {
        LoginRoute { onLogin() }
    }
}



package com.axpe.timekeeping.ui.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.axpe.timekeeping.ui.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavigation


fun NavController.navigateToHome(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(HomeNavigation, navOptionsBuilder)
}

fun NavGraphBuilder.screenHome() {
    composable<HomeNavigation> {
        HomeRoute()
    }
}
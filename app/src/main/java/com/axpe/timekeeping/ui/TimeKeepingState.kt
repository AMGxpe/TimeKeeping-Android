package com.axpe.timekeeping.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.axpe.timekeeping.navigation.TopLevelDestination
import com.axpe.timekeeping.ui.feature.home.navigation.navigateToHome
import com.axpe.timekeeping.ui.feature.login.navigation.LoginRoute

@Composable
fun rememberTimeKeepingAppState(
    navController: NavHostController = rememberNavController()
): TimeKeepingAppState = remember(
    navController
) {
    TimeKeepingAppState(navController = navController)
}

@Stable
class TimeKeepingAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val showBottomBar: Boolean
        @Composable get() = currentDestination?.route != LoginRoute::class.java.name

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries


    fun navigateToHome(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
        navController.navigateToHome(navOptionsBuilder)
    }

}
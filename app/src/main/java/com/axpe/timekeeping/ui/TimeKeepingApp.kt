package com.axpe.timekeeping.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.axpe.timekeeping.navigation.TopLevelDestination
import com.axpe.timekeeping.ui.feature.home.navigation.screenHome
import com.axpe.timekeeping.ui.feature.login.navigation.LoginRoute
import com.axpe.timekeeping.ui.feature.login.navigation.screenLogin
import com.axpe.timekeeping.ui.feature.reporting.navigation.screenReporting

@Composable
fun TimeKeepingApp(appState: TimeKeepingAppState) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = appState.showBottomBar,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                BottomAppBar {
                    appState.topLevelDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = appState.currentDestination?.route == destination.route.java.name,
                            onClick = { appState.navigateToTopLevelDestination(destination) },
                            icon = {
                                Icon(destination.unselectedIcon, contentDescription = null)
                            }
                        )
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = appState.navController,
            startDestination = LoginRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            screenLogin {
                appState.navigateToTopLevelDestination(TopLevelDestination.REPORTING)
            }
            screenHome()
            screenReporting()
        }
    }

}
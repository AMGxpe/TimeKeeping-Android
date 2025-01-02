package com.axpe.timekeeping.ui.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.axpe.timekeeping.ui.feature.profile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions) {
    navigate(ProfileRoute, navOptions)
}

fun NavGraphBuilder.screenProfile() {
    composable<ProfileRoute> {
        ProfileRoute()
    }
}
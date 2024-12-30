package com.axpe.timekeeping.ui.feature.reporting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.axpe.timekeeping.ui.feature.reporting.ReportingRoute
import kotlinx.serialization.Serializable

@Serializable
data object ReportingRoute

fun NavController.navigateToReporting(navOptions: NavOptions) =
    navigate(ReportingRoute, navOptions)

fun NavGraphBuilder.screenReporting() {
    composable<ReportingRoute> {
        ReportingRoute()
    }
}
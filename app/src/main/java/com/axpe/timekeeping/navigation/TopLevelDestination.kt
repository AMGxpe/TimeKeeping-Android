package com.axpe.timekeeping.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector
import com.axpe.timekeeping.ui.feature.home.navigation.HomeNavigation
import com.axpe.timekeeping.ui.feature.reporting.navigation.ReportingRoute
import kotlin.reflect.KClass


enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: KClass<*>
) {
    REPORTING(
        selectedIcon = Icons.Default.Create,
        unselectedIcon = Icons.Default.Create,
        route = ReportingRoute::class
    ),
    TIME_CONTROL(
        selectedIcon = Icons.Default.DateRange,
        unselectedIcon = Icons.Default.DateRange,
        route = HomeNavigation::class
    )
}
package com.axpe.timekeeping.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector
import com.axpe.timekeeping.ui.feature.home.navigation.HomeNavigation
import kotlin.reflect.KClass


enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: KClass<*>
) {
    TIME_CONTROL(
        selectedIcon = Icons.Default.DateRange,
        unselectedIcon = Icons.Default.DateRange,
        route = HomeNavigation::class
    )
}
package com.axpe.timekeeping.ui.shared.loading

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    animationType: AnimationType = AnimationType.Bounce,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    indicatorSpacing: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    val contentAlpha by animateFloatAsState(targetValue = if (loading) 0F else 1F)
    val loadingAlpha by animateFloatAsState(targetValue = if (loading) 1F else 0F)

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        Box(contentAlignment = Alignment.Center) {
            LoadingIndicator(
                animating = loading,
                modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
                color = if (enabled) colors.contentColor else colors.disabledContentColor,
                indicatorSpacing = indicatorSpacing,
                animationType = animationType
            )
            Box(modifier = Modifier.graphicsLayer { alpha = contentAlpha }) {
                content()
            }
        }
    }

}
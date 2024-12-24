package com.axpe.timekeeping.ui.shared.loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val NumIndicators = 3

private const val IndicatorsSize = 12
private const val AnimationDurationMillis = 300

private const val AnimationDelayMillis = AnimationDurationMillis / IndicatorsSize


enum class AnimationType {
    Bounce,
    Fade
}

private val AnimationType.animationDuration: Int
    get() = when (this) {
        AnimationType.Bounce -> 300
        AnimationType.Fade -> 600
    }
private val AnimationType.animationDelay: Int
    get() = animationDuration / NumIndicators

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> IndicatorsSize / 2F
        AnimationType.Fade -> 1F
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -IndicatorsSize / 2F
        AnimationType.Fade -> 0.2F
    }

@Composable
fun LoadingIndicator(
    animating: Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    indicatorSpacing: Dp = 8.dp,
    animationType: AnimationType
) {
    val animatedValues = List(NumIndicators) { index ->
        var animatedValue by remember(key1 = animating, key2 = animationType) {
            mutableFloatStateOf(
                0f
            )
        }
        LaunchedEffect(key1 = animating, key2 = animationType) {
            animate(
                initialValue = animationType.initialValue,
                targetValue = animationType.targetValue,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = animationType.animationDuration),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(animationType.animationDelay * index),
                ),
            ) { value, _ -> animatedValue = value }
        }
        animatedValue
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        animatedValues.forEach { animatedValue ->
            LoadingDot(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(12.dp)
                    .aspectRatio(1F)
                    .then(
                        when (animationType) {
                            AnimationType.Bounce -> Modifier.offset(y = animatedValue.dp)
                            AnimationType.Fade -> Modifier.graphicsLayer { alpha = animatedValue }
                        }
                    ),
                color = color
            )
        }
    }
}

@Composable
fun LoadingDot(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface
) {
    Box(
        modifier
            .clip(CircleShape)
            .background(color)
    )
}
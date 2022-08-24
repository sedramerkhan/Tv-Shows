package com.example.moviesjetpackcompose.presentation.tvShowsList.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalDottedProgressBar() {
    val color = MaterialTheme.colors.primary

    val infiniteTransition = rememberInfiniteTransition()
    val state = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
                easing = LinearEasing
            )
        )
    )

    DrawCanvas(state = state.value, radius = 15.dp, color = color)
}


@Composable
fun DrawCanvas(
    state: Float,
    radius: Dp,
    color: Color,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .height(55.dp)
                .background(MaterialTheme.colors.background)
                .align(Alignment.Center),
        ) {

            val radiusValue = radius.value
            val padding = (radiusValue + (radiusValue * 0.5f))

            for (i in 1..5) {
                if (i - 1 == state.toInt()) {
                    drawCircle(
                        radius = radiusValue * 2,
                        brush = SolidColor(color),
                        center = Offset(
                            x = this.center.x + radiusValue * 2 * (i - 3) + padding * (i - 3),
                            y = this.center.y
                        )
                    )
                } else {
                    drawCircle(
                        radius = radiusValue,
                        brush = SolidColor(color),
                        center = Offset(
                            x = this.center.x + radiusValue * 2 * (i - 3) + padding * (i - 3),
                            y = this.center.y
                        )
                    )
                }
            }
        }
    }
}


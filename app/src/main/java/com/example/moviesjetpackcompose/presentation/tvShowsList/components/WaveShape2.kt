package com.example.moviesjetpackcompose.presentation.tvShowsList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun Wave(
    height: Float,
    color: Color = MaterialTheme.colors.primary,
    elevation: Dp = 1.dp,

    ) {

    Surface(
        modifier = Modifier
            .fillMaxHeight(height)
            .fillMaxWidth(),
        shape = CustomShape(),
        elevation = elevation,
        color = color,
    ) {}
}

@Composable
fun WaveShape2(
    modifier: Modifier,
) {
    //from flutter video
    Box(modifier.background(MaterialTheme.colors.surface)) {
        Wave(.3f)
//        Wave(.25f, MaterialTheme.colors.primary.copy(alpha = .2f), 2.dp)
//        Wave(.23f)
    }

}


@Composable
fun WaveShape4() {
    Box(
        Modifier
            .padding(top = 40.dp) // the height of TopAppBar is 56
            .height(20.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface),
    ) {

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            shape = CustomShape(),
            color = MaterialTheme.colors.primary,
        ) {}
    }
}

class CustomShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            lineTo(0f, size.height / 2)
//            cubicTo(
//                size.width / 4,
//                3 * (size.height / 2),
//                3 * (size.width / 4),
//                size.height / 2,
//                size.width,
//                size.height * .9f,
//            ) //WaveShape2
//            quadraticBezierTo(size.width / 2, size.height, size.width, size.height / 2)
            cubicTo(
                size.width / 4,
                4 * (size.height / 2),
                3 * (size.width / 4),
                size.height / 10,
                size.width,
                size.height,
            ) //WaveShape4
            lineTo(size.width, 0f)
        }

        return Outline.Generic(path)
    }

}

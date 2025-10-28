package com.sm.tvshows.presentation.tvShowsList.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val ColoredWave = @Composable { modifier: Modifier, round1: Dp, round2: Dp ->
    Surface(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(bottomStart = round1, bottomEnd = round2),
        color = MaterialTheme.colors.primary,
    ) {}
}

val UnColoredWave = @Composable { modifier: Modifier, round1: Dp, round2: Dp, height: Dp->
    Box {
        Surface(
            modifier = modifier.fillMaxHeight(.5f),
            color = MaterialTheme.colors.primary,
        ) {}
        Surface(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = height),
            shape = RoundedCornerShape(topStart = round1, topEnd = round2),
        ) {}
    }
}

val Wave = @Composable { num: Int, modifier: Modifier, round1: Dp, round2: Dp, height: Dp ->
    if (num % 2 == 0) {
        ColoredWave(modifier, round1, round2)
    } else {
        UnColoredWave(modifier, round1, round2, height)
    }
}


@Composable
fun WavesSurfaces() {

    val (width, height, round) = listOf(65.dp, 30.dp, 30.dp)
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val randNum = 11
    val num = (screenWidth / (width.value - randNum)).toInt()
    val remainWidth = screenWidth % (width.value - randNum)
    val paddingStart = { n: Int ->
        width.times(num) - (randNum * n).dp
    }
    Box(
        Modifier
            .padding(top = 45.dp) // the height of TopAppBar is 56
            .height(height)
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface),
    ) {
        (0 until num).forEach {
            val modifier = Modifier
                .padding(start = paddingStart(it))
                .width(width)
            Wave(it, modifier, round, round,height/2 - 10.dp)
        }

        val modifier = Modifier
            .padding(start = paddingStart(num))
            .width((remainWidth + 5).dp)
        Wave(num, modifier, round, 0.dp,height/2 - 10.dp)
    }
}

@Composable
fun OneWaveSurface() {
    val (width, height, round) = listOf(
        (LocalConfiguration.current.screenWidthDp / 2).dp,
        180.dp,
        90.dp
    )
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val randNum = 5
    val num = (screenWidth / (width.value - randNum)).toInt()
    val remainWidth = screenWidth % (width.value - randNum)
    val paddingStart = { n: Int -> width.times(num) - (randNum * n).dp }
    Box(
        Modifier.padding(top = 45.dp) // the height of TopAppBar is 56
            .height(height).fillMaxWidth().background(MaterialTheme.colors.surface),
    ) {

        (0 until num).forEach {
            val modifier = Modifier
                .padding(start = paddingStart(it))
                .width(width)
            Wave(it, modifier, round, round, height/2 - 60.dp)
        }

        val modifier = Modifier
            .padding(start = paddingStart(num) + 0.dp)
            .width((remainWidth + 5).dp)

        Surface(
            modifier = modifier.height(102.dp),
            shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 0.dp),
            color = MaterialTheme.colors.primary,
        ) {}
        Surface(
            modifier = modifier
                .padding(start = 2.dp, top = 91.dp)
                .height(15.dp),
            shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 0.dp),
            color = MaterialTheme.colors.primary,
        ) {}
        Surface(
            modifier = modifier
                .padding(start = 4.dp, top = 103.dp)
                .height(6.dp),
            shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 0.dp),
            color = MaterialTheme.colors.primary,

            ) {}

    }
}
@androidx.compose.ui.tooling.preview.Preview
@Composable
fun WaveShapePreview() {
    WavesSurfaces()
}

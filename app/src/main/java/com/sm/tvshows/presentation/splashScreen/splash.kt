package com.sm.tvshows.presentation.splashScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.sm.tvshows.presentation.destinations.TvShowListScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import com.sm.tvshows.R

@RootNavGraph(start = true)
@Destination(style = SplashTransitions::class)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
)  {

    val duration = 500
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = duration - 50,
                easing = { OvershootInterpolator(2f).getInterpolation(it) }
            )
        )
        delay(duration.toLong())
        navigator.popBackStack()
        navigator.navigate(TvShowListScreenDestination)
    }
    Splash(scale.value)
}
@Composable
fun Splash(scale: Float) {
    Box(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            modifier = Modifier
                .size(150.dp)
                .scale(scale),
            painter = painterResource(R.drawable.ic_logo_round),
            contentDescription = "Logo Icon",
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun SplashScreenPreview() {
    Splash(1f)
}

@Composable
@androidx.compose.ui.tooling.preview.Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreview() {
    Splash(1f)
}
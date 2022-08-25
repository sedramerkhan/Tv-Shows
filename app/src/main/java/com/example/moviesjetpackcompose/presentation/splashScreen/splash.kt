package com.example.moviesjetpackcompose.presentation.splashScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.moviesjetpackcompose.presentation.destinations.TvShowListScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import com.example.moviesjetpackcompose.R

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
)  {

    var startAnimation by remember { mutableStateOf(false) }
    val time = 500
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 0f else 1f,
        animationSpec = tween(
            durationMillis = time - 50
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(time.toLong())
        navigator.popBackStack()
        navigator.navigate(TvShowListScreenDestination)
    }
    Splash(alpha = alphaAnim.value)
}
@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            modifier = Modifier
                .size(250.dp)
                .alpha(alpha = alpha),
            painter = painterResource(R.drawable.tv_show_icon),
            contentDescription = "Logo Icon",
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}

@Composable
@androidx.compose.ui.tooling.preview.Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreview() {
    Splash(alpha = 1f)
}
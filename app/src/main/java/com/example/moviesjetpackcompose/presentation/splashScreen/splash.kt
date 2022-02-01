package com.example.moviesjetpackcompose.presentation.splashScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviesjetpackcompose.R
import androidx.ui.tooling.preview.Preview

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
                .size(120.dp)
                .alpha(alpha = alpha),
            painter = painterResource(R.drawable.tv_show4),
            contentDescription = "Logo Icon",
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreview() {
    Splash(alpha = 1f)
}
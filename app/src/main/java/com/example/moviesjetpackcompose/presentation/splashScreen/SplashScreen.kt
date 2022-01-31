package com.example.moviesjetpackcompose.presentation.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.example.moviesjetpackcompose.presentation.MainActivity
import kotlinx.coroutines.delay

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var startAnimation by remember { mutableStateOf(false) }
            var startActivity by remember { mutableStateOf(false) }
            val alphaAnim = animateFloatAsState(
                targetValue = if (startAnimation) 0f else 1f,
                animationSpec = tween(
                    durationMillis = 3900
                )
            )

            LaunchedEffect(key1 = true) {
                startAnimation = true
                delay(4000)
                startActivity = true
            }
            Splash(alpha = alphaAnim.value)

            if (startActivity) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}

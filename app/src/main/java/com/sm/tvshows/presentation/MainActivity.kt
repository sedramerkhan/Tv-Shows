package com.sm.tvshows.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.sm.tvshows.presentation.theme.AppTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sm.tvshows.presentation.theme.Teal700
import com.sm.tvshows.presentation.utils.getNavigationBarPadding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


//https://androidrepo.com/repo/meshramaravind-MoviesApp-Android
@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var application: BaseApplication

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultLanguage("US")

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDark = application.isDark
            AppTheme(darkTheme = isDark) {
                BoxWithConstraints(Modifier.background(MaterialTheme.colors.surface).navigationBarsPadding()) {
                    val width = constraints.maxWidth
                    val height = constraints.maxHeight
                    
                    val navController = rememberNavController()
                    val currentBackStack by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStack?.destination?.route

                    Log.i("currentRoute", currentRoute ?: "null")
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        engine = navHostEngine(IntOffset(width, height)),
                        navController = navController
                    )
                    
                    // Update status bar text color based on current destination
                    val isSplashScreen = currentRoute?.contains("splash_screen") == true
                    SetSystemBarColors(
                        isDark = isDark,
                        isSplashScreen = isSplashScreen
                    )
                }
            }
        }
    }

    @Composable
    private fun SetSystemBarColors(isDark: Boolean, isSplashScreen: Boolean = false){
        SideEffect {
            val controller = WindowInsetsControllerCompat(window, window.decorView)
            // If splash screen, use black text; otherwise use theme-based text color
            controller.isAppearanceLightStatusBars = isSplashScreen
            controller.isAppearanceLightNavigationBars =  if (isSplashScreen) true else !isDark
        }
    }

    private fun setDefaultLanguage(lang: String?) {
        val locale = Locale.forLanguageTag(lang ?: "en-US")
        Locale.setDefault(locale)

        // Modern way to set locale (API 24+)
        val config = resources.configuration
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

//https://github.com/raamcosta/compose-destinations/issues/41
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
val navHostEngine = @Composable { offset: IntOffset ->
    rememberAnimatedNavHostEngine(
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = {
                slideIn(
                    initialOffset = { offset },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 800, //it's bigger than slideIn duration to not show black background
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOut(
                    targetOffset = { offset },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ),
    )
}

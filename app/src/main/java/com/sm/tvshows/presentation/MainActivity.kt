package com.sm.tvshows.presentation

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import com.sm.tvshows.presentation.theme.AppTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
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
        setContent {
            val isDark = application.isDark
            AppTheme(darkTheme = isDark) {

                BoxWithConstraints {
                    val width = constraints.maxWidth
                    val height = constraints.maxHeight
                    DestinationsNavHost(
                        navGraph =
                            NavGraphs.root,
                        engine = navHostEngine(IntOffset(width, height))
                    )
                }
            }
        }
    }

    private fun setDefaultLanguage(lang: String?) {
        val locale = Locale.forLanguageTag(lang ?: "en-US")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources.updateConfiguration(config, this.resources.displayMetrics)
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

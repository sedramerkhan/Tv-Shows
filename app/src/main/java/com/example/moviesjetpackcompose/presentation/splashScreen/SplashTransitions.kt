package com.example.moviesjetpackcompose.presentation.splashScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.example.moviesjetpackcompose.presentation.appDestination
import com.example.moviesjetpackcompose.presentation.destinations.SplashScreenDestination

import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object SplashTransitions : DestinationStyle.Animated {

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {

        return when (initialState.appDestination()) {
            SplashScreenDestination ->
                fadeIn(animationSpec = tween(500)) + expandVertically(tween(500))
            else -> null
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        return when (targetState.appDestination()) {
            SplashScreenDestination ->
                fadeOut(animationSpec = tween(500)) + shrinkOut(tween(500))
            else -> null
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {

        return when (initialState.appDestination()) {
            SplashScreenDestination -> fadeIn(animationSpec = tween(500))
            else -> null
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {

        return when (targetState.appDestination()) {
            SplashScreenDestination -> fadeOut(animationSpec = tween(500))
            else -> null
        }
    }
}
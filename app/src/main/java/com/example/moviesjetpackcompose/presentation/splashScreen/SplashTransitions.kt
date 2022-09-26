package com.example.moviesjetpackcompose.presentation.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.example.moviesjetpackcompose.presentation.appDestination
import com.example.moviesjetpackcompose.presentation.destinations.SplashScreenDestination

import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object SplashTransitions : DestinationStyle.Animated {

    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition? {

        return when (initialState.appDestination()) {
            SplashScreenDestination ->
                fadeIn(animationSpec = tween(500)) + expandVertically(tween(500))
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition? {

        return when (targetState.appDestination()) {
            SplashScreenDestination ->
                fadeOut(animationSpec = tween(500)) + shrinkOut(tween(500))
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {

        return when (initialState.appDestination()) {
            SplashScreenDestination -> fadeIn(animationSpec = tween(500))
            else -> null
        }
    }

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {

        return when (targetState.appDestination()) {
            SplashScreenDestination -> fadeOut(animationSpec = tween(500))
            else -> null
        }
    }
}
package com.sm.tvshows.presentation.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable

// Extension function to get status bar padding
@Composable
fun getStatusBarPadding(): androidx.compose.ui.unit.Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}

@Composable
fun getNavigationBarPadding(): androidx.compose.ui.unit.Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}

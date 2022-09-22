package com.example.moviesjetpackcompose.presentation.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.moviesjetpackcompose.presentation.theme.Teal700

@Composable
fun FailureView(
    isDark: Boolean,
    connectionState: Boolean,
    onClicked: () -> Unit,
) {
    val (icon, text) = when (connectionState) {
        false -> Icons.Default.Refresh to "No Internet Connection"
        else -> Icons.Default.WarningAmber to "Failed to get data"
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClicked) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Teal700,
                modifier = Modifier
                    .size(200.dp)
                    .alpha(.5f)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface
        )
        if (!connectionState)
            Text(
                text = "Check your connection, then reload",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface
            )
    }
}
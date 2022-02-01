package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandedText(
    description: String,
    expandedState: Boolean,
    onClick: () -> Unit,
) {
    val (maxLines, buttonText) = when (expandedState) {
        true -> Int.MAX_VALUE to "less"
        false -> 4 to "more"
    }

    Text(
        text = description,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(horizontal = 16.dp),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.onSurface
    )

    Text(
        text = buttonText,
        color = Color.Blue,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 10.dp)
            .clickable(
                onClick = onClick
            )
    )

}
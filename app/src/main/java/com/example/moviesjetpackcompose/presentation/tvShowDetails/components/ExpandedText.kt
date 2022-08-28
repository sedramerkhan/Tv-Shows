package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
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
    var isLineEllipsized by remember {
        mutableStateOf(false)
    }

    Text(
        text = description,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(horizontal = 16.dp),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.onSurface,
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            if (maxLines != Int.MAX_VALUE && textLayoutResult.hasVisualOverflow) {
                isLineEllipsized = textLayoutResult.isLineEllipsized(maxLines-1)
            }
        }
    )
    Log.i("Line", isLineEllipsized.toString())
    if (isLineEllipsized) {
        Text(
            text = buttonText,
            color = Color.Blue,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable(
                    onClick = onClick
                )
        )
    }
    Spacer(Modifier.height(10.dp))
}
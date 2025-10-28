package com.sm.tvshows.presentation.tvShowDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sm.tvshows.presentation.theme.Gold


@Composable
fun RowRatingGenresTime(
    rating: Double,
    runtime: Int,
    genres: List<String>
) {
    Spacer(Modifier.height(10.dp))
    Divider(
        color = Color.Gray,
        thickness = 1.dp
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Filled.Star, contentDescription = "rating", tint = Gold)
        Text(
            text = "$rating /$runtime Min",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onSurface
        )

    }
    if (genres.isNotEmpty())
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = genres.joinToString(),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onSurface
        )

    Spacer(Modifier.height(10.dp))
    Divider(
        color = Color.Gray,
        thickness = 1.dp
    )
}
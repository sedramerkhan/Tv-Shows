package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun TvShowView(
    tvShow: TvShowDetails,
    expandedState: Boolean,
    onClick: () -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Column {

                ConstraintItems(tvShow = tvShow)
                ExpandedText(
                    description = tvShow.description,
                    expandedState = expandedState,
                    onClick = onClick
                )

                RowRatingGenresTime(rating = tvShow.rating, runtime = tvShow.runtime, genres =tvShow.genres )
            }
        }
    }
}
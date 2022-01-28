package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun TvShowView(
    tvShow: TvShowDetails,
    expandedState: Boolean,
    onClickExpand: () -> Unit,
    onClickEpisodes: () -> Unit
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
                    onClick = onClickExpand
                )

                RowRatingGenresTime(
                    rating = tvShow.rating,
                    runtime = tvShow.runtime,
                    genres = tvShow.genres
                )
                Button(
                    onClick = onClickEpisodes
                ) {
                    Text(
                        text = "Episodes",
                        style = MaterialTheme.typography.h4,
                    )
                }
            }
        }
    }
}
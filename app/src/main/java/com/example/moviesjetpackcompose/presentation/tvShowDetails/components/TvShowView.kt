package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.presentation.theme.Teal700
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun TvShowView(
    tvShow: TvShowDetails,
    expandedState: Boolean,
    imageIndex: Int,
    onClickExpand: () -> Unit,
    onClickEpisodes: () -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Column {

                ConstraintItems(tvShow = tvShow, imageIndex = imageIndex)
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
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp, vertical = 16.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    onClick = onClickEpisodes,
                   colors = ButtonDefaults.buttonColors(backgroundColor = Teal700)

                ) {
                    Text(
                        text = "Episodes",
                        style = MaterialTheme.typography.h4,
                        color = Color.White
                    )
                }
            }
        }
    }
}
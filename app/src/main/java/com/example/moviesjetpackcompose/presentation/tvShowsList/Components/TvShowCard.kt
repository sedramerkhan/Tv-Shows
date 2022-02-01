package com.example.moviesjetpackcompose.presentation.tvShowsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.CoilImage
import com.example.moviesjetpackcompose.presentation.theme.Green300

@ExperimentalCoilApi
@Composable
fun TvShowCard(
    tvShow: TvShow,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {

        Row {
            CoilImage(
                link = tvShow.image_thumbnail_path,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp),
                imageModifier = Modifier.height(130.dp).width(110.dp),
                roundCorner = 20f
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = tvShow.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = tvShow.network + " (" + tvShow.country + ")",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface
                )
                tvShow.start_date?.let {
//                    var end = tvShow.end_date ?: ""
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                Text(
                    text = tvShow.status,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.subtitle1,
                    color = Green300
                )
            }
        }
    }
}


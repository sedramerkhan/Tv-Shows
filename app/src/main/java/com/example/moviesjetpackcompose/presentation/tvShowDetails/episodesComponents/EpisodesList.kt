package com.example.moviesjetpackcompose.presentation.tvShowDetails.episodesComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesjetpackcompose.domain.model.Episode
import com.example.moviesjetpackcompose.presentation.tvShowsList.PAGE_SIZE
import com.example.moviesjetpackcompose.presentation.tvShowsList.TvShowCard

@Composable
fun EpisodesList(episodes: List<Episode>,name: String){

    Column() {

        Text(
            text = "Episodes | $name",
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            itemsIndexed(
                items = episodes
            ) { index, episode ->
//            onChangeScrollPosition(index)

                EpisodesCard(
                    episode = episode,
                )
            }
        }
    }
}
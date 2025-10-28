package com.sm.tvshows.presentation.tvShowDetails.episodesComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sm.tvshows.domain.model.Episode
import com.sm.tvshows.presentation.tvShowsList.PAGE_SIZE
import com.sm.tvshows.presentation.tvShowsList.TvShowCard

@Composable
fun EpisodesList(
    episodes: List<Episode>,
    name: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        Text(
            text = "Episodes | $name",
            style = when {
                name.length < 18 -> MaterialTheme.typography.h2
                else -> MaterialTheme.typography.h4
            },
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(5.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 60.dp)
        ) {
            items(episodes) { episode ->
                EpisodesCard(ep = episode)
            }
        }
    }

}
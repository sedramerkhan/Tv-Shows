package com.sm.tvshows.presentation.tvShowDetails.episodesComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sm.tvshows.domain.model.Episode

@Composable
fun EpisodesBottomDrawer(
    episodes: List<Episode>,
    name: String,
) {
    Surface(                    //To add Padding to Drawer
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = 4.dp
    ) {
        if (episodes.isNotEmpty())
            EpisodesList(
                episodes = episodes,
                name = name,
            )
        else
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp, top = 50.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "There are No Episodes",
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primary,
                )
            }
    }
}

package com.example.moviesjetpackcompose.presentation.tvShowDetails.episodesComponents

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
import com.example.moviesjetpackcompose.domain.model.Episode
import com.example.moviesjetpackcompose.presentation.tvShowsList.PAGE_SIZE
import com.example.moviesjetpackcompose.presentation.tvShowsList.TvShowCard

@Composable
fun EpisodesList(
    episodes: List<Episode>,
    name: String,
    onClickClose: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            ) {
                Text(
                    text = "Episodes | $name",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(.9f)

                )
                IconButton(
                    onClick = onClickClose,
                    modifier = Modifier.wrapContentWidth(Alignment.End)

                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close dialog",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                items(episodes){ episode ->
                    EpisodesCard(ep = episode)
                }
            }
        }
    }

}
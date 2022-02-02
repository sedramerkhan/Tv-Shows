package com.example.moviesjetpackcompose.presentation.tvShowDetails.episodesComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesjetpackcompose.domain.model.Episode

@Composable
fun EpisodesCard(
    ep: Episode
){
    Card(
        modifier = Modifier.padding(bottom = 16.dp,start=20.dp,end=20.dp).fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp
//        backgroundColor = MaterialTheme.colors.surface,
    ){
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            with(ep){
                val s = season.toString()
                val season = if(s.length>1) s else "0$s"
                val e = episode.toString()
                val episodeNum = if(e.length>1) e else "0$e"
                Text(
                    text = "S${season}E${episodeNum}",
                    style = MaterialTheme.typography.h3,
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4,
                )
                Text(
                    text = "Air Date: ${air_date}",
                    style = MaterialTheme.typography.h5,
                )
            }

        }
    }
}
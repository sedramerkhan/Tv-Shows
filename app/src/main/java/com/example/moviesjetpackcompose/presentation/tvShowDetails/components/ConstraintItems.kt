package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.presentation.theme.Green300
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.CoilImage

@ExperimentalCoilApi
@Composable
fun ConstraintItems(
    tvShow: TvShowDetails,

){
    ConstraintLayout(Modifier.fillMaxWidth().padding(bottom = 10.dp)) {

        val (imageRef, thumbnailRef, textRef) = createRefs()
        CoilImage(
            link = tvShow.pictures[0],
            modifier = Modifier.constrainAs(imageRef) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)

            },

            imageModifier = Modifier
                .height(IMAGE_HEIGHT.dp)
                .fillMaxWidth(),
            loading = true
        )
        CoilImage(
            link = tvShow.image_thumbnail_path,
            modifier = Modifier
                .padding(horizontal = 13.dp)
                .constrainAs(thumbnailRef) {
                    top.linkTo(imageRef.bottom)
                    bottom.linkTo(imageRef.bottom)
                    start.linkTo(parent.start)
                },
            imageModifier = Modifier
                .height(220.dp)
                .width(140.dp),
            roundCorner = 20f
        )
        Column(modifier = Modifier.fillMaxWidth().constrainAs(textRef) {
            top.linkTo(imageRef.bottom)
            start.linkTo(thumbnailRef.end)
        }) {
            Text(
                text = tvShow.name,
                style = if(tvShow.name.length <18) MaterialTheme.typography.h3 else if(tvShow.name.length >22) MaterialTheme.typography.subtitle1 else  MaterialTheme.typography.h4,
            )
            Text(
                text = tvShow.network + " (" + tvShow.country + ")",
                style = MaterialTheme.typography.h5,
            )
            Text(
                text = tvShow.status,
                style = MaterialTheme.typography.subtitle1,
                color = Green300,
            )
            tvShow.start_date?.let {
//                          var end = tvShow.end_date ?: ""
                Text(
                    text = "Started On: $it",
                    style = MaterialTheme.typography.h5,
                )
            }
        }
    }
}
package com.example.moviesjetpackcompose.presentation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
import com.example.moviesjetpackcompose.R


/***
add android:usesCleartextTraffic="true" to manifest
*/
@Composable
fun CoilImage(
    link: String = "https://static.episodate.com/images/tv-show/thumbnail/49417.jpg",
){
    Box(
        modifier = Modifier.height(150.dp).width(80.dp)
    ){

        var painter = rememberImagePainter(
            data = link,
            builder = {
                placeholder(R.drawable.place_holder)
                error(R.drawable.error)
                crossfade(1000)
                transformations(
                    RoundedCornersTransformation(50f)
//                    GrayscaleTransformation(),
//                    CircleCropTransformation(),
//                    BlurTransformation(LocalContext.current),
                )
                //this. to know all other functions
//                press ctrl + q to show function description
            }
        )
        var painterState = painter.state
        Image(painter= painter, contentDescription = null)
        if (painterState is ImagePainter.State.Loading ){
            CircularProgressIndicator()
        }
    }
}
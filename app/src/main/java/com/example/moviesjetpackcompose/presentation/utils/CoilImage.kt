package com.example.moviesjetpackcompose.presentation.tvShowsList.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.example.moviesjetpackcompose.R


/***
add android:usesCleartextTraffic="true" to manifest
 */
@ExperimentalCoilApi
@Composable
fun CoilImage(
    link: String,
) {
    Box(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
    ) {

        val painter = rememberImagePainter(
            data = link,
            builder = {
                placeholder(R.drawable.place_holder)
                error(R.drawable.error)
                crossfade(1000)
                transformations(
                    RoundedCornersTransformation(20f)
//                    GrayscaleTransformation(),
//                    CircleCropTransformation(),
//                    BlurTransformation(LocalContext.current),
                )

                //this. to know all other functions
//                press ctrl + q to show function description
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(130.dp)
                .width(110.dp)
        )
//        var painterState = painter.state
//        if (painterState is ImagePainter.State.Loading ){
//            CircularProgressIndicator()
//        }
    }
}
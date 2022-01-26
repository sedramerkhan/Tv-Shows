package com.example.moviesjetpackcompose.presentation.tvShowsList.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.example.moviesjetpackcompose.R
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar


/***
add android:usesCleartextTraffic="true" to manifest
 */
@ExperimentalCoilApi
@Composable
fun CoilImage(
    link: String,
    modifier: Modifier = Modifier,
    imageModifier: Modifier,
    roundCorner: Float = 0f,
    loading: Boolean = false
    ) {
    Box(
        modifier = modifier
    ) {

        val painter = rememberImagePainter(
            data = link,
            builder = {
                placeholder(R.drawable.place_holder)
                error(R.drawable.error)
                crossfade(1000)
                transformations(
                    RoundedCornersTransformation(roundCorner)
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
            contentScale = ContentScale.FillBounds,
            modifier = imageModifier
        )
        var painterState = painter.state
        if (painterState is ImagePainter.State.Loading && loading ){
            CircularIndeterminateProgressBar(isDisplayed = loading, verticalBias = 0.25f)
        }
    }
}
package com.example.moviesjetpackcompose.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import coil.transform.RoundedCornersTransformation
import com.example.moviesjetpackcompose.R


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
    placeholder: Int = R.drawable.placeholder,
    loading: Boolean = false
) {
    Box(
        modifier = modifier
    ) {

        val painter = rememberAsyncImagePainter(
                Builder(LocalContext.current).data(data = link)
                    .apply(//                    GrayscaleTransformation(),
                        //                    CircleCropTransformation(),
                        //                    BlurTransformation(LocalContext.current),

                        //this. to know all other functions
                        //                press ctrl + q to show function description
                        block = { ->
                            placeholder(placeholder)
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
                        }).build()
            )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = imageModifier
        )
        val painterState = painter.state
        if (painterState is AsyncImagePainter.State.Loading && loading) {
            CircularIndeterminateProgressBar(verticalBias = 0.5f)
        }
    }
}
package com.sm.tvshows.presentation.tvShowDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.sm.tvshows.R
import com.sm.tvshows.domain.model.TvShowDetails
import com.sm.tvshows.presentation.theme.Green300
import com.sm.tvshows.presentation.utils.CoilImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun ConstraintItems(
    tvShow: TvShowDetails,
) {
    var linesCount by remember {
        mutableStateOf(1)
    }
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {

        val (imageRef, thumbnailRef, textRef) = createRefs()
        val pictureModifier = Modifier.constrainAs(imageRef) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
        }
        tvShow.pictures.let {
            val size = if (it.isEmpty()) 1 else it.size
            Surface( modifier = pictureModifier,color= Color.LightGray)
            {
                HorizontalPager(count = size,) { imageIndex ->
                    CoilImage(
                        link = if (it.isEmpty()) "" else it[imageIndex],
                        modifier = pictureModifier,
                        imageModifier = Modifier
                            .height(IMAGE_HEIGHT.dp)
                            .fillMaxWidth(),
                        placeholder = R.drawable.placeholder_transparent

                    )
                }
            }
        }
        CoilImage(
            link = tvShow.image_thumbnail_path,
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = (12 * (linesCount - 1) * (linesCount - 1)).dp
                )
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
        Column(modifier = Modifier
            .fillMaxWidth(.55f)
            .constrainAs(textRef) {
                top.linkTo(imageRef.bottom)
                start.linkTo(thumbnailRef.end)
            }) {
            Text(
                text = tvShow.name,
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start),
                style = when {
                    tvShow.name.length < 18 -> MaterialTheme.typography.h3
                    tvShow.name.length > 22 -> MaterialTheme.typography.subtitle1
                    else -> MaterialTheme.typography.h4
                },
                color = MaterialTheme.colors.onSurface,
                onTextLayout = { textLayoutResult: TextLayoutResult ->
                    linesCount = textLayoutResult.lineCount
                }
            )
            Text(
                text = tvShow.network + " (" + tvShow.country + ")",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = tvShow.status,
                style = MaterialTheme.typography.subtitle1,
                color = Green300,
            )
            tvShow.start_date?.let {
//               var end = tvShow.end_date ?: ""
                Text(
                    text = "Started On: $it",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}
package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.presentation.theme.Green300
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun TvShowView(
    tvShow: TvShowDetails,
){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Column {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(430.dp)
                ) {
                    ConstraintLayout(Modifier.fillMaxWidth()) {


                        val (imageRef, nameRef,networkRef,dateRef,statusRef) = createRefs()
                        CoilImage(
                            link = tvShow.pictures[0],
                            modifier = Modifier .constrainAs(imageRef){
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            },
                            imageModifier = Modifier
                                .height(IMAGE_HEIGHT.dp)
                                .fillMaxWidth(),
                            loading = true
                        )
                        val startBias = createGuidelineFromStart(170.dp)
                        Text(text = tvShow.name,
                            style = MaterialTheme.typography.h3,
                        modifier = Modifier.constrainAs(nameRef){
                            top.linkTo(imageRef.bottom)
                            end.linkTo(imageRef.end)
                            start.linkTo(startBias)

                        })
                        Text(
                            text = tvShow.network + " (" + tvShow.country + ")",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.constrainAs(networkRef){
                                    top.linkTo(nameRef.bottom)
                                    start.linkTo(startBias)

                                })

                        Text(
                        text = tvShow.status,
                            style = MaterialTheme.typography.subtitle1,
                            color = Green300,
                        modifier = Modifier.constrainAs(statusRef){
                            top.linkTo(networkRef.bottom)
                            start.linkTo(startBias)
                        })
                        tvShow.start_date?.let {
//                    var end = tvShow.end_date ?: ""
                            Text(
                                text = "Started On: $it",
                                style = MaterialTheme.typography.h5,
                                modifier =  Modifier.constrainAs(dateRef){
                                    top.linkTo(statusRef.bottom)
                                    start.linkTo(startBias)
                                }
                            )
                        }

                    }
                    CoilImage(
                        link = tvShow.image_thumbnail_path,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp)
                            .align(
                                Alignment.BottomStart
                            ),
                        imageModifier = Modifier
                            .height(220.dp)
                            .width(140.dp),
                        roundCorner = 20f
                    )

                }

            }
        }
    }
}
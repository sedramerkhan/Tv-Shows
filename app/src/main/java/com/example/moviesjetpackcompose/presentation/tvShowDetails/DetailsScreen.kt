package com.example.moviesjetpackcompose.presentation.tvShowDetails

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.presentation.utils.FailureView
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.IMAGE_HEIGHT
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.LoadingTvShowShimmer
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.TvShowView
import com.example.moviesjetpackcompose.presentation.tvShowDetails.episodesComponents.EpisodesList
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalCoroutinesApi::class,
    ExperimentalCoilApi::class
)
@Destination()
@Composable
fun TvShowDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: TvShowDetailsViewModel = hiltViewModel(),
    id: String,
) = viewModel.run {

    val scaffoldState = rememberScaffoldState()

    val isDark = application.isDark

    BackHandler(enabled = dialogState, onBack = {
        viewModel.setDialogState()

    })

    Scaffold(
        scaffoldState = scaffoldState,
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (loading && tvShow == null && !failure)
                LoadingTvShowShimmer(imageHeight = IMAGE_HEIGHT.dp)

            tvShow?.let {
                TvShowView(
                    tvShow = it,
                    expandedState = expandedState,
                    onClickExpand = viewModel::setExpandedState,
                    onClickEpisodes = viewModel::setDialogState
                )
            }

            CircularIndeterminateProgressBar(
                isDisplayed = loading && !failure,
                verticalBias = 0.3f
            )

            if (failure) {
                FailureView(isDark = isDark)
            }

            if (dialogState) {
                Box(
                    modifier = Modifier
                        .background(
                            color = contentColorFor(Color.Black)
                                .copy(alpha = if (isDark) .5f else .8f)
                        )
//                                    .clickable(
//                                        interactionSource = remember { MutableInteractionSource() },
//                                        indication = null,
//                                        onClick = viewModel::setDialogState
//                                    )
                    , contentAlignment = Alignment.Center
                ) {
                    tvShow?.let {
                        EpisodesList(
                            episodes = it.episodes,
                            name = it.name,
                            onClickClose = viewModel::setDialogState
                        )
                    }
                }
            }
        }
    }
}
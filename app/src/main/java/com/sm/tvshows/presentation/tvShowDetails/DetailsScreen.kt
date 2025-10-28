package com.sm.tvshows.presentation.tvShowDetails


import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.sm.tvshows.network.NetworkResult
import com.sm.tvshows.presentation.utils.FailureView
import com.sm.tvshows.presentation.tvShowDetails.components.IMAGE_HEIGHT
import com.sm.tvshows.presentation.tvShowDetails.components.LoadingTvShowShimmer
import com.sm.tvshows.presentation.tvShowDetails.components.TvShowView
import com.sm.tvshows.presentation.tvShowDetails.episodesComponents.EpisodesBottomDrawer
import com.sm.tvshows.presentation.utils.CircularIndeterminateProgressBar
import com.sm.tvshows.presentation.utils.InternetConnection.ConnectionState
import com.sm.tvshows.presentation.utils.InternetConnection.connectivityState
import de.charlex.compose.BottomDrawerScaffold
import de.charlex.compose.BottomDrawerValue
import de.charlex.compose.rememberBottomDrawerScaffoldState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalCoroutinesApi::class,
    ExperimentalCoilApi::class, ExperimentalMaterialApi::class
)
@Destination
@Composable
fun TvShowDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: TvShowDetailsViewModel = hiltViewModel(),
    id: String,
) = viewModel.run {

    val connectionState by connectivityState()
    var failureMessageState by remember { mutableStateOf(true) }

    val topHeight = with(LocalDensity.current) { (IMAGE_HEIGHT / 2).dp.toPx() }
    val scaffoldState = rememberBottomDrawerScaffoldState(drawerTopInset = topHeight.toInt())
    val bottomDrawerState = scaffoldState.bottomDrawerState

    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = bottomDrawerState.isExpanded) {
        coroutineScope.launch { bottomDrawerState.collapse() }
    }

    //https://camposha.info/jetpack-compose/compose-bottom-drawer/
    BottomDrawerScaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = true,
        drawerPeekHeight = 5.dp,
        drawerBackgroundColor = Color.Transparent,  //Transparent drawer for custom Drawer shape
        drawerElevation = 0.dp,
        drawerContent = {
            tvShowResponse?.let {
                when (it) {
                    is NetworkResult.Success -> {
                        it.data.run {
                            EpisodesBottomDrawer(
                                episodes = episodes,
                                name = name,
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            tvShowResponse?.let {
                when (it) {
                    is NetworkResult.Loading -> {
                        LoadingTvShowShimmer(imageHeight = IMAGE_HEIGHT.dp)

                        CircularIndeterminateProgressBar(verticalBias = 0.3f)
                    }

                    is NetworkResult.Failure -> {
                        if (connectionState == ConnectionState.Unavailable)
                            failureMessageState = false

                        FailureView(
                            isDark = application.isDark,
                            connectionState = failureMessageState
                        ) {
                            onTriggerEvent(TvShowDetailsEvent.GetTvShowDetailsEvent(id))
                            failureMessageState = true
                        }
                    }

                    is NetworkResult.Success -> {
                        TvShowView(
                            tvShow = it.data,
                            expandedState = expandedState,
                            onClickExpand = viewModel::setExpandedState,
                            onClickEpisodes = {
                                coroutineScope.launch {
                                    bottomDrawerState.animateTo(
                                        BottomDrawerValue.Expanded,
                                        tween(300)
                                    )
                                }

                            }
                        )
                    }
                }
            }


        }
    }

}
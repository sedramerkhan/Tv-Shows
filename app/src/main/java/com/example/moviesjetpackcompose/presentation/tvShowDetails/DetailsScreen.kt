package com.example.moviesjetpackcompose.presentation.tvShowDetails


import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.presentation.utils.FailureView
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.IMAGE_HEIGHT
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.LoadingTvShowShimmer
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.TvShowView
import com.example.moviesjetpackcompose.presentation.tvShowDetails.episodesComponents.EpisodesBottomDrawer
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import de.charlex.compose.BottomDrawerScaffold
import de.charlex.compose.BottomDrawerValue
import de.charlex.compose.rememberBottomDrawerScaffoldState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalCoroutinesApi::class,
    ExperimentalCoilApi::class, ExperimentalMaterialApi::class
)
@Destination()
@Composable
fun TvShowDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: TvShowDetailsViewModel = hiltViewModel(),
    id: String,
) = viewModel.run {

    val topBarHeight = with(LocalDensity.current) { 56.dp.toPx() }
    val scaffoldState = rememberBottomDrawerScaffoldState(drawerTopInset = topBarHeight.toInt())
    val bottomDrawerState = scaffoldState.bottomDrawerState

    val coroutineScope = rememberCoroutineScope()

    val isDark = application.isDark

    BackHandler(enabled = bottomDrawerState.isExpanded, onBack = {
        coroutineScope.launch { bottomDrawerState.collapse() }
    })

    //https://camposha.info/jetpack-compose/compose-bottom-drawer/
    BottomDrawerScaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = true,
        drawerPeekHeight = 5.dp,
        drawerBackgroundColor = Color.Transparent,  //Transparent drawer for custom Drawer shape
        drawerElevation = 0.dp,
        drawerContent = {
            tvShow?.run{
               EpisodesBottomDrawer(
                    episodes =episodes,
                    name = name,
                )
            }

        }
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
                    onClickEpisodes = {
                        coroutineScope.launch {
                            bottomDrawerState.animateTo(BottomDrawerValue.Expanded, tween(300) )
                        }

                    }
                )
            }

            CircularIndeterminateProgressBar(
                isDisplayed = loading && !failure,
                verticalBias = 0.3f
            )

            if (failure) {
                FailureView(isDark = isDark)
            }

        }
    }

}
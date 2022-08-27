package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.presentation.destinations.TvShowDetailsScreenDestination
import com.example.moviesjetpackcompose.presentation.utils.FailureView
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.MainAppBar
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.SearchWidgetState
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.TvShowList
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.*


@OptIn(ExperimentalCoroutinesApi::class, ExperimentalComposeUiApi::class,
    ExperimentalCoilApi::class, DelicateCoroutinesApi::class
)
@Destination
@Composable
fun TvShowListScreen(
    navigator: DestinationsNavigator,
    viewModel: TvShowListViewModel = hiltViewModel(),
)  = viewModel.run{

    val scaffoldState = rememberScaffoldState()
    var startAnimation by remember { mutableStateOf(false) }

    BackHandler(enabled = searchWidgetState == SearchWidgetState.OPENED) {
        GlobalScope.launch {
            startAnimation = true
            viewModel.setSearchState(SearchWidgetState.CLOSED)
            viewModel.onTriggerEvent(TvShowListEvent.RestoreStateEvent)
            delay(100)
            startAnimation = false
        }
    }

    Scaffold(
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                query = query,
                isDark = application.isDark,
                startAnimation = startAnimation,
                keyboardState = keyboardState,
                onQueryChanged = viewModel::onQueryChanged,
                onExecuteSearch = {
                    viewModel.onTriggerEvent(TvShowListEvent.NewSearchEvent)
                },
                onCloseClicked = {
                    GlobalScope.launch {
                        startAnimation = true
                        viewModel.setSearchState(SearchWidgetState.CLOSED)
                        viewModel.onTriggerEvent(TvShowListEvent.RestoreStateEvent)
                        delay(100)
                        startAnimation = false
                    }
                },
                onSearchTriggered = {
                    GlobalScope.launch {
                        startAnimation = true
                        delay(500)
                        viewModel.setSearchState(SearchWidgetState.OPENED)
                        startAnimation = false
                    }

                },
                onToggleTheme = { application.toggleLightTheme() },
            )
        },
        scaffoldState = scaffoldState,
    ) {
        TvShowList(
            loading = loading,
            tvShows = tvShows,
            onChangeScrollPosition = viewModel::onChangeTvShowScrollPosition,
            page = page,
            onTriggerNextPage = { viewModel.onTriggerEvent(TvShowListEvent.NextPageEvent) },
            onNavigateToTvShowsDetailScreen = {
                viewModel.setKeyboardState()
               navigator.navigate(TvShowDetailsScreenDestination(it))
            },
            listState = listState,
            setListState = viewModel::setListState
        )
        if (failure) {
            FailureView(isDark =application.isDark)
        }

        if (loading && tvShows.isNotEmpty()) {
            Log.d("loading", tvShows.size.toString())
            CircularIndeterminateProgressBar(
                isDisplayed = loading,
                verticalBias = 0.1f
            )
        }

    }
}


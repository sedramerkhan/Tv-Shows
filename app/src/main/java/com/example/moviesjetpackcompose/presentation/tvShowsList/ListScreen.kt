package com.example.moviesjetpackcompose.presentation.tvShowsList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.network.NetworkResult
import com.example.moviesjetpackcompose.presentation.destinations.TvShowDetailsScreenDestination
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.HorizontalDottedProgressBar
import com.example.moviesjetpackcompose.presentation.utils.FailureView
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.MainAppBar
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.SearchWidgetState
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.TvShowList
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import com.example.moviesjetpackcompose.presentation.utils.InternetConnection.ConnectionState
import com.example.moviesjetpackcompose.presentation.utils.InternetConnection.connectivityState
import com.example.moviesjetpackcompose.presentation.utils.SplashTransitions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.*


@OptIn(
    ExperimentalCoroutinesApi::class, ExperimentalComposeUiApi::class,
    ExperimentalCoilApi::class, DelicateCoroutinesApi::class,
)
@Destination(style = SplashTransitions::class)
@Composable
fun TvShowListScreen(
    navigator: DestinationsNavigator,
    viewModel: TvShowListViewModel = hiltViewModel(),
) = viewModel.run {

    val connectionState by connectivityState()
    var failureMessageState by remember { mutableStateOf(true) }

    val scaffoldState = rememberScaffoldState()
    var startAnimation by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    BackHandler(enabled = searchWidgetState == SearchWidgetState.OPENED) {
        GlobalScope.launch {
            startAnimation = true
            setSearchState(SearchWidgetState.CLOSED)
            onQueryChanged("")
            onTriggerEvent(TvShowListEvent.RestoreStateEvent)
            delay(100)
            startAnimation = false
        }
    }

    LaunchedEffect(key1 = listStateTo0) {
        if (listStateTo0) {
            listState.animateScrollToItem(0)
            setListState()
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
                onQueryChanged = ::onQueryChanged,
                onExecuteSearch = {
                    onTriggerEvent(TvShowListEvent.NewSearchEvent)
                },
                onCloseClicked = {
                    GlobalScope.launch {
                        startAnimation = true
                        setSearchState(SearchWidgetState.CLOSED)
                        onTriggerEvent(TvShowListEvent.RestoreStateEvent)
                        delay(100)
                        startAnimation = false
                    }
                },
                onSearchTriggered = {
                    GlobalScope.launch {
                        startAnimation = true
                        delay(500)
                        setSearchState(SearchWidgetState.OPENED)
                        startAnimation = false
                    }

                },
                onToggleTheme = { application.toggleLightTheme() },
            )
        },
        scaffoldState = scaffoldState,
    ) {

        Surface {
            TvShowList(
                tvShows = tvShows,
                onChangeScrollPosition = ::onChangeTvShowScrollPosition,
                page = page,
                onTriggerNextPage = { onTriggerEvent(TvShowListEvent.NextPageEvent) },
                onNavigateToTvShowsDetailScreen = {
                    setKeyboardState()
                    navigator.navigate(TvShowDetailsScreenDestination(it))
                },
                state = listState,
            )

            when (tvShowsResponse) {
                is NetworkResult.Loading -> {
                    if (tvShows.isEmpty()) {
                        HorizontalDottedProgressBar()
                    } else {
                        CircularIndeterminateProgressBar(verticalBias = 0.1f)
                    }
                }
                is NetworkResult.Failure -> {
                    if (connectionState == ConnectionState.Unavailable)
                        failureMessageState = false

                    FailureView(
                        isDark = application.isDark,
                        connectionState = failureMessageState
                    ) {
                        onTriggerEvent(TvShowListEvent.GetMostPopular)
                        failureMessageState = true
                    }
                }
                else -> {
                    if (tvShows.isEmpty() && query.isNotEmpty() and query.isNotBlank())
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(25.dp), contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                text = "Nothing Match \"$query\"",
                                style = MaterialTheme.typography.h3,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                }
            }
        }
    }

}




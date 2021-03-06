package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.R
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.TvShowList
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.FailureView
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.SearchWidgetState
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.MainAppBar
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class TvShowsListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: TvShowListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val tvShows = viewModel.tvShows.value
                val query = viewModel.query.value
                val loading = viewModel.loading.value
                val page = viewModel.page.value
                val isDark = application.isDark.value
                val scaffoldState = rememberScaffoldState()
                val searchWidgetState = viewModel.searchWidgetState.value
                val listState = viewModel.listState.value
                val failure = viewModel.failure.value
                val keyboardState = viewModel.keyboardState.value
                var startAnimation by remember { mutableStateOf(false) }

                AppTheme(
                    darkTheme = isDark,
                ) {

                    Scaffold(
                        topBar = {
                            MainAppBar(
                                searchWidgetState = searchWidgetState,
                                query = query,
                                isDark = isDark,
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
                                onToggleTheme = application::toggleLightTheme,
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
                                val bundle = Bundle()
                                bundle.putString("tvShowId", it)
                                findNavController().navigate(
                                    R.id.action_moviesListFragment_to_movieFragment,
                                    bundle
                                )
                            },
                            listState = listState,
                            setListState = viewModel::setListState
                        )
                        if (failure) {
                            FailureView(isDark = isDark)
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
            }
        }
    }
}


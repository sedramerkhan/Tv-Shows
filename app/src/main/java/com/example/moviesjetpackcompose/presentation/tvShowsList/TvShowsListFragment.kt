package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.R
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.TvShowList
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.SearchWidgetState
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.TopAppBar1
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
                val searchDone = viewModel.searchDone.value
                var startAnimation by remember { mutableStateOf(false) }

                AppTheme(
                    darkTheme = isDark,
                ) {

                    Scaffold(
                        topBar = {
                            TopAppBar1(
                                searchWidgetState = searchWidgetState,
                                query = query,
                                isDark = isDark,
                                startAnimation = startAnimation,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    viewModel.onTriggerEvent(TvShowListEvent.NewSearchEvent)
                                },
                                onCloseClicked = {
                                    GlobalScope.launch {
                                        startAnimation = true
                                        viewModel.setSearchState(SearchWidgetState.CLOSED)
                                        if (searchDone)
                                            viewModel.onTriggerEvent(TvShowListEvent.RestoreStateEvent)
                                        delay(1000)
                                        startAnimation = false
                                    }
                                },
                                onSearchTriggered = {
                                    GlobalScope.launch {
                                        startAnimation = true
                                        delay(1000)
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
                                val bundle = Bundle()
                                bundle.putString("tvShowId", it)
                                findNavController().navigate(
                                    R.id.action_moviesListFragment_to_movieFragment,
                                    bundle
                                )
                            }
                        )

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


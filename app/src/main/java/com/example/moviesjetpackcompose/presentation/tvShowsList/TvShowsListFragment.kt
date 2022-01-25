package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.moviesjetpackcompose.R
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.Components.SearchAppBar
import com.example.moviesjetpackcompose.presentation.Components.TvShowList
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TvShowsListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication


    private val viewModel: TvShowListViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
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

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                ) {

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {

                                        viewModel.onTriggerEvent(TvShowListEvent.NewSearchEvent)
                                },
                                onToggleTheme = application::toggleLightTheme
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        },

                        ) {
                        TvShowList(
                            loading = loading,
                            tvShows = tvShows,
                            onChangeScrollPosition = viewModel::onChangeTvShowScrollPosition,
                            page = page,
                            onTriggerNextPage = { viewModel.onTriggerEvent(TvShowListEvent.NextPageEvent) },
                            onNavigateToTvShowsDetailScreen = {
                                val bundle = Bundle()
                                bundle.putString("recipeId", it)
                                findNavController().navigate(R.id.action_moviesListFragment_to_movieFragment, bundle)
                            }
                        )
                    }
                }
            }
        }
    }
}


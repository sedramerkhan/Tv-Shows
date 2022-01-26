package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.moviesjetpackcompose.R
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.SearchAppBar
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.TvShowList
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

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

                AppTheme(
                    darkTheme = isDark,
                ) {

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                isDark = isDark,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {

                                    viewModel.onTriggerEvent(TvShowListEvent.NewSearchEvent)
                                },
                                onToggleTheme = application::toggleLightTheme
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
                                bundle.putString("recipeId", it)
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


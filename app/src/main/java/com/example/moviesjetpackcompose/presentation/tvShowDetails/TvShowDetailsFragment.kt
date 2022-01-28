package com.example.moviesjetpackcompose.presentation.tvShowDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.theme.AppShapes
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.IMAGE_HEIGHT
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.LoadingTvShowShimmer
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.TvShowView
import com.example.moviesjetpackcompose.presentation.tvShowDetails.episodesComponents.EpisodesList
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class TvShowDetailsFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: TvShowDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("tvShowId")?.let { tvShowId ->
            viewModel.onTriggerEvent(TvShowDetailsEvent.GetTvShowDetailsEvent(tvShowId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply{
            setContent {

                val loading = viewModel.loading.value
                val failure = viewModel.failure.value
                val tvShow = viewModel.tvShow.value
                val expandedState = viewModel.expandedState.value

                val scaffoldState = rememberScaffoldState()

                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = rememberBottomSheetState(
                        initialValue = BottomSheetValue.Collapsed
                    )
                )
//                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
//                    bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
//                )
                val coroutineScope = rememberCoroutineScope()


                AppTheme(
                    darkTheme = application.isDark.value,
                ){
                    BottomSheetScaffold(
                        scaffoldState = bottomSheetScaffoldState,
                        sheetContent ={
                            tvShow?.let {   EpisodesList(episodes =it.episodes,name= tvShow.name) }
                                      },
                        sheetShape = AppShapes.small,
                        sheetPeekHeight = 0.dp
                    ) {



                        Box (
                            modifier = Modifier.fillMaxSize()
                        ){

                            if (loading && tvShow == null)
                                LoadingTvShowShimmer(imageHeight = IMAGE_HEIGHT.dp)
                            tvShow?.let {
                                    TvShowView(
                                        tvShow = it,
                                        expandedState = expandedState,
                                        onClickExpand = viewModel::setExpandedState,
                                        onClickEpisodes = {
                                            OpenBottomSheet(
                                                bottomSheetScaffoldState = bottomSheetScaffoldState,
                                                coroutineScope = coroutineScope
                                            )
                                        }

                                    )

                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading, verticalBias = 0.3f)

                            if(failure){
                                Text(
                                    text = "Failed to get information",
                                    style = MaterialTheme.typography.h1,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    fun OpenBottomSheet(
        bottomSheetScaffoldState: BottomSheetScaffoldState,
        coroutineScope:CoroutineScope
    ){
        coroutineScope.launch {
            Log.d("sese","Button Clicked ${bottomSheetScaffoldState.bottomSheetState.isCollapsed}")
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                bottomSheetScaffoldState.bottomSheetState.expand()

            } else {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    }
}

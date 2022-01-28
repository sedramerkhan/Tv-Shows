package com.example.moviesjetpackcompose.presentation.tvShowDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.IMAGE_HEIGHT
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.LoadingTvShowShimmer
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.TvShowView
import com.example.moviesjetpackcompose.presentation.tvShowDetails.episodesComponents.EpisodesList
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val fragment = this
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value
                val failure = viewModel.failure.value
                val tvShow = viewModel.tvShow.value
                val expandedState = viewModel.expandedState.value
                val dialogState = viewModel.dialogState.value
                val isDark= application.isDark.value
                val scaffoldState = rememberScaffoldState()


//                BackHandler(onBack = {
//
////                             fragmentManager?.popBackStack()
////                      activity?.supportFragmentManager?.beginTransaction()?.remove(fragment)?.commit()
////                      activity?.supportFragmentManager?.popBackStack()
//
//                })

                AppTheme(
                    darkTheme = isDark,
                ) {

                        Scaffold(
                            scaffoldState = scaffoldState,

                            ) {

                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                            if (loading && tvShow == null)
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
                                isDisplayed = loading,
                                verticalBias = 0.3f
                            )

                            if (failure) {
                                Text(
                                    text = "Failed to get information",
                                    style = MaterialTheme.typography.h1,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .width(200.dp)
                                )
                            }

                            if (dialogState) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = contentColorFor(Color.Black)
                                                .copy(alpha =if(isDark) .5f else .8f )
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
                                            name = tvShow.name,
                                            onClickClose = viewModel::setDialogState
                                        )
                                    }
                                }
                            }
                        }


                    }
                }
            }
        }
    }

}

package com.example.moviesjetpackcompose.presentation.tvShowDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.R
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.IMAGE_HEIGHT
import com.example.moviesjetpackcompose.presentation.tvShowDetails.components.LoadingTvShowShimmer
import com.example.moviesjetpackcompose.presentation.tvShowsList.TvShowListViewModel
import com.example.moviesjetpackcompose.presentation.utils.CircularIndeterminateProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TvShowDetailsFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: TvShowDetailsViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply{
            setContent {

                val loading = viewModel.loading.value

                val tvShow = viewModel.tvShow.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                ){
                    Scaffold(
                        scaffoldState = scaffoldState,
                    ) {
                        Box (
                            modifier = Modifier.fillMaxSize()
                        ){
                            if (loading && tvShow == null) LoadingTvShowShimmer(imageHeight = IMAGE_HEIGHT.dp)
                            else tvShow?.let {

//                                    TvShowView(
//                                        tvShow = it,
//                                    )

                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading, verticalBias = 0.3f)

                        }
                    }
                }
            }
        }
    }
}

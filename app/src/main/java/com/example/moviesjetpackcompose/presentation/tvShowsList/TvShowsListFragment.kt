package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.moviesjetpackcompose.presentation.BaseApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
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

                val scaffoldState = rememberScaffoldState()

                Column {

                    Text(
                        text = tvShows.size.toString(),
                        modifier = Modifier
                            .padding(50.dp)
                            .size(50.dp)
                            .background(Color.White)
                            .align(CenterHorizontally)
                    )
                    LazyColumn {
                        itemsIndexed(
                            items = tvShows
                        ) { index, tvShow ->
//                        onChangeScrollPosition(index)
//                            if ((index + 1) >= (page * 100) && !loading) {
//                                onTriggerNextPage()
//                            }
                            Text(
                                text = tvShow.name,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(20.dp)
                                    .align(CenterHorizontally)
                            )
                        }
                    }

                }


            }
        }
    }
}

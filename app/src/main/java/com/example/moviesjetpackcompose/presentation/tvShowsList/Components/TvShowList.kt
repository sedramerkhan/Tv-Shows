package com.example.moviesjetpackcompose.presentation.tvShowsList.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.presentation.tvShowsList.PAGE_SIZE
import com.example.moviesjetpackcompose.presentation.tvShowsList.TvShowCard

@ExperimentalCoilApi
@Composable
fun TvShowList(
    loading: Boolean,
    tvShows: List<TvShow>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToTvShowsDetailScreen: (String) -> Unit,
    listState: Boolean,
    setListState: () -> Unit,
){
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && tvShows.isEmpty()) {
            HorizontalDottedProgressBar()
        }
        else if(tvShows.isNotEmpty()){

            val state = rememberLazyListState()
           LaunchedEffect(listState) {
                   state.animateScrollToItem(0)
                   setListState()
               }

            LazyColumn(
                state = state
            ){
                itemsIndexed(
                    items = tvShows,
                ) { index, tvShow ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }
                    TvShowCard(
                        tvShow = tvShow,
                        onClick = { onNavigateToTvShowsDetailScreen(tvShow.id)
                        }
                    )
                }
            }
        }
    }
}



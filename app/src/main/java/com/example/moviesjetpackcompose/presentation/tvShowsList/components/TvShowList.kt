package com.example.moviesjetpackcompose.presentation.tvShowsList.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.presentation.tvShowsList.PAGE_SIZE
import com.example.moviesjetpackcompose.presentation.tvShowsList.TvShowCard
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalCoilApi
@Composable
fun TvShowList(
    loading: Boolean,
    tvShows: List<TvShow>,
    onChangeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToTvShowsDetailScreen: (String) -> Unit,
    state: LazyListState,
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (loading && tvShows.isEmpty()) {
            HorizontalDottedProgressBar()
        } else if (tvShows.isNotEmpty()) {
            LazyColumn(
                state = state
            ) {
                itemsIndexed(
                    items = tvShows,
                ) { index, tvShow ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }
                    TvShowCard(
                        tvShow = tvShow,
                        onClick = {
                            onNavigateToTvShowsDetailScreen(tvShow.id)
                        }
                    )
                }
            }
        }
    }
}



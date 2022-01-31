package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.presentation.tvShowsList.Components.SearchWidgetState
import com.example.moviesjetpackcompose.repository.TvShowRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_PAGE = "tvShow.state.page.key"
const val STATE_KEY_QUERY = "tvShow.state.query.key"
const val STATE_KEY_LIST_POSITION = "tvShow.state.query.list_position"

const val PAGE_SIZE = 20

@ExperimentalCoroutinesApi
@HiltViewModel
class TvShowListViewModel
@Inject
constructor(
    private val repo: TvShowRepo,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var TAG = "Debug"

    val tvShows: MutableState<List<TvShow>> = mutableStateOf(ArrayList())
    val searchDone = mutableStateOf(false)
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    val searchWidgetState = mutableStateOf(SearchWidgetState.CLOSED)
    var tvShowListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
//        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
//            setQuery(q)
//        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        Log.d("init", "$tvShowListScrollPosition  ${page.value}  ${query.value}")
        if (tvShowListScrollPosition != 0) {
            onTriggerEvent(TvShowListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(TvShowListEvent.GetMostPopular)
        }
    }

    fun onTriggerEvent(event: TvShowListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is TvShowListEvent.GetMostPopular -> {
                        getMostPopular()
                    }
                    is TvShowListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is TvShowListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is TvShowListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }


    private suspend fun getMostPopular() {
        loading.value = true
        resetSearchState()
        delay(2000)
        val result = repo.getPopular(
            page = 1
        )
        tvShows.value = result
        searchDone.value = false
        loading.value = false
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<TvShow> = mutableListOf()

        for (p in 1..page.value) {
            val result = repo.getPopular(page = p)
            results.addAll(result)
            if (p == page.value) { // done
                tvShows.value = results
                loading.value = false
            }
            Log.d("restoreState", results.size.toString())
        }
    }

    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        delay(2000)
        val results: MutableList<TvShow> = mutableListOf()

        for (p in 1..page.value) {
            val result = repo.search(
                page = p,
                query = query.value
            )
            results.addAll(result)
            if (p == page.value) { // done
                tvShows.value = results
                searchDone.value = true
                loading.value = false
            }
            Log.d("newSearch", results.size.toString())
        }
    }

    private suspend fun nextPage() {
        // prevent duplicate event due to recompose happening to quickly
        if ((tvShowListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            delay(1000)

            if (page.value > 1) {
                val result = repo.getPopular(page = page.value)
                Log.d(TAG, "appending " + result.size.toString())
                appendTvShows(result)
            }
            loading.value = false
        }
    }


    private fun appendTvShows(recipes: List<TvShow>) {
        val current = ArrayList(this.tvShows.value)
        current.addAll(recipes)
        this.tvShows.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeTvShowScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        tvShows.value = listOf()
        onChangeTvShowScrollPosition(0)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setListScrollPosition(position: Int) {
        tvShowListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }


    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    fun setSearchState(state: SearchWidgetState) {
        searchWidgetState.value = state
    }

}

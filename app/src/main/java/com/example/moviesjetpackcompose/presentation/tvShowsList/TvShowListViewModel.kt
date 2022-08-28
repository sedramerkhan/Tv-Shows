package com.example.moviesjetpackcompose.presentation.tvShowsList

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.tvShowsList.components.SearchWidgetState
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
    application: BaseApplication,
    private val repo: TvShowRepo,
    private val savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {
    private var TAG = "App Debug"
    
    val application
        get() = getApplication<BaseApplication>()

    val tvShows = mutableStateListOf<TvShow>()
    private var searchDone by mutableStateOf(false)
    var query by mutableStateOf("")
    var loading by mutableStateOf(false)
    var page by mutableStateOf(1)
    var searchWidgetState by mutableStateOf(SearchWidgetState.CLOSED)
    private var tvShowListScrollPosition = 0
    var failure by mutableStateOf(false)
    var listStateTo0 by mutableStateOf(false)
    var keyboardState by mutableStateOf(true) //it's for keyboard and focusRequester

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
        Log.d("init", "$tvShowListScrollPosition  $page  $query")
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
        loading = true
        resetSearchState()
        delay(2000)
        val result = repo.getPopular(
            page = 1
        )
        if (result.isEmpty())
            failure = true
        tvShows.clear()
        tvShows.addAll(result)
        loading = false
        keyboardState = true
    }

    private suspend fun restoreState() {
        if (searchDone) {
            loading = true
            val results: MutableList<TvShow> = mutableListOf()

            for (p in 1..page) {
                val result = repo.getPopular(page = p)
                results.addAll(result)
                if (p == page) { // done
                    tvShows.clear()
                    tvShows.addAll(result)
                    loading = false
                    searchDone = false
                    setListState()
                }
                Log.d("restoreState", results.size.toString())
            }
        }
    }

    private suspend fun newSearch() {
        loading = true
        resetSearchState()
        delay(2000)
        val results: MutableList<TvShow> = mutableListOf()

        for (p in 1..page) {
            val result = repo.search(
                page = p,
                query = query
            )
            results.addAll(result!!)
            if (p == page) { // done
                tvShows.clear()
                tvShows.addAll(result)
                searchDone = true
                loading = false
                setListState()
            }
            Log.d("newSearch", results.size.toString())
        }
    }

    private suspend fun nextPage() {
        // prevent duplicate event due to recompose happening to quickly
        if (!searchDone) {
            if ((tvShowListScrollPosition + 1) >= (page * PAGE_SIZE)) {
                loading = true
                incrementPage()
                Log.d(TAG, "nextPage: triggered: ${page}")

                delay(1000)

                if (page > 1) {
                    val result = repo.getPopular(page = page)
                    Log.d(TAG, "appending " + result.size.toString())
                    appendTvShows(result)
                }
                loading = false
            }
        }
    }


    private fun appendTvShows(recipes: List<TvShow>) {
        this.tvShows.addAll(recipes)
    }

    private fun incrementPage() {
        setPage(page + 1)
    }

    fun onChangeTvShowScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        tvShows.clear()
        onChangeTvShowScrollPosition(0)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setListScrollPosition(position: Int) {
        tvShowListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    @JvmName("setPage1")
    private fun setPage(page: Int) {
        this.page = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }


    @JvmName("setQuery1")
    private fun setQuery(query: String) {
        this.query = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    fun setSearchState(state: SearchWidgetState) {
        searchWidgetState = state
    }

    // it is called when close button for search is clicked or new search is done
    fun setListState() {
        listStateTo0 = !listStateTo0
    }

    fun setKeyboardState() {
        keyboardState = false
    }

}

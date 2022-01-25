package com.example.moviesjetpackcompose.presentation.tvShowsList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.repository.TvShowRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class TvShowListViewModel
@Inject
constructor(
    private val repo: TvShowRepo,
    private val state: SavedStateHandle,
): ViewModel() {
    val recipes: MutableState<List<TvShow>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val loading = mutableStateOf(false)

    // Pagination starts at '1' (-1 = exhausted)
    val page = mutableStateOf(1)

    var recipeListScrollPosition = 0

    init {
        getData()
    }
     fun getData() = viewModelScope.launch {
        loading.value = true

//        resetSearchState()

        delay(2000)

        val result = repo.getPopular(
            page = 1
        )
        recipes.value = result

        loading.value = false
    }


}

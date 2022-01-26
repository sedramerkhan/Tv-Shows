package com.example.moviesjetpackcompose.presentation.tvShowDetails

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.repository.TvShowRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_TV_SHOW = "tvShow.state.tvShow.key"

@ExperimentalCoroutinesApi
@HiltViewModel
class TvShowDetailsViewModel
@Inject
constructor(
    private val repo: TvShowRepo,
    private val state: SavedStateHandle,
) : ViewModel() {

    var TAG = "App Debug"

    val tvShow: MutableState<TvShowDetails?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    init {
        // restore if process dies
        state.get<String>(STATE_KEY_TV_SHOW)?.let { recipeId ->
            onTriggerEvent(TvShowDetailsEvent.GetTvShowDetailsEvent(recipeId))
        }
    }

    fun onTriggerEvent(event: TvShowDetailsEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is TvShowDetailsEvent.GetTvShowDetailsEvent -> {
                        if (tvShow.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getRecipe(id: String) {
        loading.value = true

        // simulate a delay to show loading
        delay(1000)

        val recipe = repo.getDetails(id)
        this.tvShow.value = recipe

        state.set(STATE_KEY_TV_SHOW, recipe.id)

        loading.value = false
    }
}
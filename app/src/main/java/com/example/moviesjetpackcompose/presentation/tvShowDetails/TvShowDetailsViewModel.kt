package com.example.moviesjetpackcompose.presentation.tvShowDetails

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val failure = mutableStateOf(false)
    val expandedState = mutableStateOf(false)
    val dialogState = mutableStateOf(false)
    val imageIndex = mutableStateOf(0)

    init {
        // restore if process dies
        state.get<String>(STATE_KEY_TV_SHOW)?.let { tvShowId ->
            onTriggerEvent(TvShowDetailsEvent.GetTvShowDetailsEvent(tvShowId))
        }
    }

    fun onTriggerEvent(event: TvShowDetailsEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is TvShowDetailsEvent.GetTvShowDetailsEvent -> {
                        if (tvShow.value == null) {
                            getDetails(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getDetails(id: String) {
        loading.value = true
        // simulate a delay to show loading
        delay(1000)
        Log.d("soso", "hello from get Details")
        repo.getDetails(id, ::apiCallback)

    }

    fun setFailure() {
        failure.value = true
    }

    fun apiCallback(tvShow: TvShowDetails?) {
        tvShow?.let {
            this.tvShow.value = it
            Log.d("soso", it.name)
            state.set(STATE_KEY_TV_SHOW, it.id)
        } ?: setFailure()

        loading.value = false
    }

    fun setExpandedState() {
        expandedState.value = !expandedState.value
    }

    fun setDialogState() {
        dialogState.value = !dialogState.value
    }

    fun setImageIndex() {
        tvShow.value?.let {
            imageIndex.value++
            if (it.pictures.size == imageIndex.value) {
                imageIndex.value = 0
            }
        }

    }
}
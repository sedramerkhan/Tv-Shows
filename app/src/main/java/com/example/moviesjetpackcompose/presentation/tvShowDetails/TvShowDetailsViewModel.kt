package com.example.moviesjetpackcompose.presentation.tvShowDetails

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.presentation.destinations.TvShowDetailsScreenDestination
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
    application: BaseApplication,
    private val repo: TvShowRepo,
    private val state: SavedStateHandle,
) : AndroidViewModel(application) {
    private var TAG = "App Debug"

    val application
        get() = getApplication<BaseApplication>()

    var tvShow by mutableStateOf<TvShowDetails?>(null)
    var loading by mutableStateOf(false)
    var failure by mutableStateOf(false)
    var expandedState by mutableStateOf(false)
    var dialogState by mutableStateOf(false)
    var imageIndex by mutableStateOf(0)
    private val tvShowId = TvShowDetailsScreenDestination.argsFrom(state).id

    init {
        // restore if process dies
        state.get<String>(STATE_KEY_TV_SHOW)?.let { tvShowId ->
            onTriggerEvent(TvShowDetailsEvent.GetTvShowDetailsEvent(tvShowId))
        }

        onTriggerEvent(TvShowDetailsEvent.GetTvShowDetailsEvent(tvShowId))

    }

    fun onTriggerEvent(event: TvShowDetailsEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is TvShowDetailsEvent.GetTvShowDetailsEvent -> {
                        if (tvShow == null) {
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
        loading = true
        // simulate a delay to show loading
        delay(1000)
        Log.d("soso", "hello from get Details")
        repo.getDetails(id, ::apiCallback)

    }

    fun setFailure() {
        failure = true
    }

    fun apiCallback(tvShow: TvShowDetails?) {
        tvShow?.let {
            this.tvShow = it
            Log.d("soso", it.name)
            state.set(STATE_KEY_TV_SHOW, it.id)
        } ?: setFailure()

        loading = false
    }

    fun setExpandedState() {
        expandedState = !expandedState
    }

    fun setDialogState() {
        dialogState = !dialogState
    }

    fun setImageIndex() {
        tvShow?.let {
            imageIndex++
            if (it.pictures.size == imageIndex) {
                imageIndex = 0
            }
        }

    }
}
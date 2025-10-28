package com.sm.tvshows.presentation.tvShowDetails

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sm.tvshows.domain.model.TvShowDetails
import com.sm.tvshows.network.NetworkResult
import com.sm.tvshows.presentation.BaseApplication
import com.sm.tvshows.presentation.destinations.TvShowDetailsScreenDestination
import com.sm.tvshows.repository.TvShowRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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

    var tvShowResponse by mutableStateOf<NetworkResult<TvShowDetails>?>(null)
    var expandedState by mutableStateOf(false)

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
                        state.set(STATE_KEY_TV_SHOW, event.id)
                            getDetails(event.id)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getDetails(id: String) {
        Log.d("soso", "hello from get Details")
        repo.getDetails(id).collect {
            tvShowResponse = it
            Log.d("soso", it.toString())
        }
    }


    fun setExpandedState() {
        expandedState = !expandedState
    }

}
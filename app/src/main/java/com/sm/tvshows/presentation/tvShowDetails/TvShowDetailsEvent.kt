package com.sm.tvshows.presentation.tvShowDetails

sealed class TvShowDetailsEvent {

    data class GetTvShowDetailsEvent(
        val id: String
    ) : TvShowDetailsEvent()

}
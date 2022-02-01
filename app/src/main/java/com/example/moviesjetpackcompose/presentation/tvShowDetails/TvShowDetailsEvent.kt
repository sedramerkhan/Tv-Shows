package com.example.moviesjetpackcompose.presentation.tvShowDetails

sealed class TvShowDetailsEvent {

    data class GetTvShowDetailsEvent(
        val id: String
    ) : TvShowDetailsEvent()

}
package com.example.moviesjetpackcompose.presentation.tvShowsList

sealed class TvShowListEvent {

    object NewSearchEvent : TvShowListEvent()

    object GetMostPopular : TvShowListEvent()

    object NextPageEvent : TvShowListEvent()

    // restore after process death
    object RestoreStateEvent: TvShowListEvent()
}
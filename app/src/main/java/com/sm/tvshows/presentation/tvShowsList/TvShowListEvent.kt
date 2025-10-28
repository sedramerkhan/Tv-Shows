package com.sm.tvshows.presentation.tvShowsList

sealed class TvShowListEvent {

    object NewSearchEvent : TvShowListEvent()

    object GetMostPopular : TvShowListEvent()

    object NextPageEvent : TvShowListEvent()

    // restore after process death or search end
    object RestoreStateEvent : TvShowListEvent()
}
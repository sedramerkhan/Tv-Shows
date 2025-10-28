package com.sm.tvshows.domain.model

data class Episode(
    val season: Int,
    val episode: Int,
    val name: String,
    val airDate: String?
)
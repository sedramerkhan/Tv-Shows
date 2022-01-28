package com.example.moviesjetpackcompose.domain.model

data class Episode(
    val season: Int,
    val episode: Int,
    val name: String,
    val air_date: String?
)
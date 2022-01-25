package com.example.moviesjetpackcompose.domain.model

data class TvShow(
    val id: String,
    val name: String,
    val permalink: String,
    val start_date: String?,
    val end_date: String?,
    val country: String,
    val network: String,
    val status: String,
    val image_thumbnail_path: String
)
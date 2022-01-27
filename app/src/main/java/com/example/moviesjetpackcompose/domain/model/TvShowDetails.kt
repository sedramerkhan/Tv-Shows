package com.example.moviesjetpackcompose.domain.model

data class TvShowDetails(
    val id: Int,
    val name: String,
    val permalink: String,
    val url: String,
    val description: String,
    val description_source: String?,
    val start_date: String?,
    val end_date: String?,
    val country: String,
    val status: String,
    val runtime: Int,
    val network: String,
    val youtube_link: String?,
    val image_path: String,
    val image_thumbnail_path: String,
    val rating: Double,
    val rating_count: Int,
    val countdown: String?,
    val genres: List<String>,
    val pictures: List<String>,
    val episodes: List<Episode>
)
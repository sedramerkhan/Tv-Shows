package com.example.moviesjetpackcompose.network.model

import com.google.gson.annotations.SerializedName

data class EpisodesDto(
    @SerializedName("season") val season: Int,
    @SerializedName("episode") val episode: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val air_date: String?
)
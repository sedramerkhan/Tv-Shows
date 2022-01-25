package com.example.moviesjetpackcompose.network.response

import com.example.moviesjetpackcompose.network.model.TvShowDto
import com.google.gson.annotations.SerializedName


data class ResponseTvShows(
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("tv_shows") val tv_shows: List<TvShowDto>
)

package com.sm.tvshows.network.model

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName("season") val season: Int,
    @SerializedName("episode") val episode: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String?
)
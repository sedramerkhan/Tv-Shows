package com.sm.tvshows.network.response

import com.sm.tvshows.network.model.TvShowDetailsDto
import com.google.gson.annotations.SerializedName


data class ResponseShowDetails(@SerializedName("tvShow") val tvShow : TvShowDetailsDto)
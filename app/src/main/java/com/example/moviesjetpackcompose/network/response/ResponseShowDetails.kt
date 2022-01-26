package com.example.moviesjetpackcompose.network.response

import com.example.moviesjetpackcompose.network.model.TvShowDetailsDto
import com.google.gson.annotations.SerializedName


data class ResponseShowDetails(@SerializedName("tvShow") val tvShow : TvShowDetailsDto)
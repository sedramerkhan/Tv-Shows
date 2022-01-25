package com.example.moviesjetpackcompose.network.response

import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.google.gson.annotations.SerializedName


data class ResponseShowDetails(@SerializedName("tvShow") val tvShow : TvShowDetails)
package com.example.moviesjetpackcompose.network

import com.example.moviesjetpackcompose.network.response.ResponseTvShows
import com.example.moviesjetpackcompose.network.response.ResponseShowDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("most-popular")
    suspend fun getMostPopularTVShows(@Query("page") page: Int): Response<ResponseTvShows>

    @GET("show-details")
    suspend fun getTVShowDetails(@Query("q") tvShowId: String?): Response<ResponseShowDetails>

    @GET("search")
    suspend fun searchTVShow(
        @Query("q") query: String?,
        @Query("page") page: Int
    ): Response<ResponseTvShows>
}

/**
 * if we use Response the function must be suspend but it doesn't need to be suspend with Call
 */
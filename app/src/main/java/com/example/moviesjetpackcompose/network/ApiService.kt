package com.example.moviesjetpackcompose.network

import com.example.moviesjetpackcompose.network.response.ResponseTvShows
import com.example.moviesjetpackcompose.network.response.ResponseShowDetails
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("most-popular")
    suspend fun getMostPopularTVShows(@Query("page") page: Int): Response<ResponseTvShows>

    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId: String?): Call<ResponseShowDetails>

    @GET("search")
    suspend fun searchTVShow(
        @Query("q") query: String?,
        @Query("page") page: Int
    ): Response<ResponseTvShows>
}
package com.example.moviesjetpackcompose.repository

import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface TvShowRepo {

    suspend fun search(page: Int, query: String):  Flow<NetworkResult<List<TvShow>>>

    suspend fun getPopular( page: Int):   Flow<NetworkResult<List<TvShow>>>

    suspend fun getDetails(id: String?) : Flow<NetworkResult<TvShowDetails>>
}
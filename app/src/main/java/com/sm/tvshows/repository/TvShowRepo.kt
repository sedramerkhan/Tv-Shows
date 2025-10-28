package com.sm.tvshows.repository

import com.sm.tvshows.domain.model.TvShow
import com.sm.tvshows.domain.model.TvShowDetails
import com.sm.tvshows.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface TvShowRepo {

    suspend fun search(page: Int, query: String):  Flow<NetworkResult<List<TvShow>>>

    suspend fun getPopular( page: Int):   Flow<NetworkResult<List<TvShow>>>

    suspend fun getDetails(id: String?) : Flow<NetworkResult<TvShowDetails>>
}
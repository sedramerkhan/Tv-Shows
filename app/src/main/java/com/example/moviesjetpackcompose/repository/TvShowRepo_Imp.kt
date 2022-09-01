package com.example.moviesjetpackcompose.repository

import android.util.Log
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.network.ApiService
import com.example.moviesjetpackcompose.network.mapper.TvShowDetailsMapper
import com.example.moviesjetpackcompose.network.mapper.TvShowMapper
import com.example.moviesjetpackcompose.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TvShowRepo_Imp(
    private val apiService: ApiService,
    private val tvShowMapper: TvShowMapper,
    private val tvShowDetailsMapper: TvShowDetailsMapper
) : TvShowRepo {
    private val _errorMessage = "Failed"
    override suspend fun search(page: Int, query: String): Flow<NetworkResult<List<TvShow>>> =
        flow {
            emit(NetworkResult.Loading(true))
            kotlinx.coroutines.delay(1000)

            val response = apiService.searchTVShow(page = page, query = query)
            emit(NetworkResult.Success(tvShowMapper.toDomainList(response.body()?.tv_shows!!)))
        }.catch { e ->
            emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        }


    override suspend fun getPopular(page: Int): Flow<NetworkResult<List<TvShow>>> = flow {
        emit(NetworkResult.Loading(true))
        kotlinx.coroutines.delay(1000)
        val response = apiService.getMostPopularTVShows(page)
        emit(NetworkResult.Success(tvShowMapper.toDomainList(response.body()?.tv_shows!!)))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }


    override suspend fun getDetails(id: String?): Flow<NetworkResult<TvShowDetails>> {
        return flow {
            emit(NetworkResult.Loading(true))
            kotlinx.coroutines.delay(500)
            val response = apiService.getTVShowDetails(id)
            response.body()?.tvShow?.let {
                emit(NetworkResult.Success(tvShowDetailsMapper.mapToDomainModel(it)))
            }
        }.catch { e ->
            Log.i("Details", e.message.toString())
            emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
        }

    }


}
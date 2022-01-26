package com.example.moviesjetpackcompose.repository

import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.network.ApiService
import com.example.moviesjetpackcompose.network.mapper.TvShowDetailsMapper
import com.example.moviesjetpackcompose.network.mapper.TvShowMapper

class TvShowRepo_Imp(
    private val apiService: ApiService,
    private val tvShowMapper: TvShowMapper,
    private val tvShowDetailsMapper: TvShowDetailsMapper
) : TvShowRepo{
    override suspend fun search(page: Int, query: String): List<TvShow> =
        tvShowMapper.toDomainList(apiService.searchTVShow(page = page, query = query).tv_shows)

    override suspend fun getPopular(page: Int): List<TvShow> {
        return tvShowMapper.toDomainList(apiService.getMostPopularTVShows(page).tv_shows)
    }

    override suspend fun getDetails(query: String): TvShowDetails {
        return tvShowDetailsMapper.mapToDomainModel(apiService.getTVShowDetails(query).tvShow)
    }

}
package com.example.moviesjetpackcompose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.network.ApiService
import com.example.moviesjetpackcompose.network.mapper.TvShowDetailsMapper
import com.example.moviesjetpackcompose.network.mapper.TvShowMapper
import com.example.moviesjetpackcompose.network.model.TvShowDetailsDto
import com.example.moviesjetpackcompose.network.response.ResponseShowDetails
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    override suspend fun getDetails( id: String?, callback: (TvShowDetails?) -> Unit) {

        apiService.getTVShowDetails(id).enqueue(object : Callback<ResponseShowDetails?> {
            override fun onResponse(
                call: Call<ResponseShowDetails?>,
                response: Response<ResponseShowDetails?>
            ) {
                Log.d("soso",response.body()?.tvShow?.network ?: "fail")
                val tvShow = response.body()?.tvShow
                val res =  tvShow?.let { tvShowDetailsMapper.mapToDomainModel(it) }
                callback(res)
            }

            override fun onFailure(call: Call<ResponseShowDetails?>, t: Throwable) {
                Log.d("soso","onFailure")
                callback(null)
            }
        })

    }


}
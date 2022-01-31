package com.example.moviesjetpackcompose.repository

import com.example.moviesjetpackcompose.domain.model.TvShow
import com.example.moviesjetpackcompose.domain.model.TvShowDetails

interface TvShowRepo {

    suspend fun search(page: Int, query: String): List<TvShow>?

    suspend fun getPopular( page: Int):  List<TvShow>

    suspend fun getDetails(id: String?, callback: (TvShowDetails?) ->Unit)
}
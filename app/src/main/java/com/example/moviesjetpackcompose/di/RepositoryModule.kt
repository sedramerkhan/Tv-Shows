package com.example.moviesjetpackcompose.di

import com.example.moviesjetpackcompose.network.ApiService
import com.example.moviesjetpackcompose.network.mapper.TvShowDetailsMapper
import com.example.moviesjetpackcompose.network.mapper.TvShowMapper
import com.example.moviesjetpackcompose.repository.TvShowRepo
import com.example.moviesjetpackcompose.repository.TvShowRepo_Imp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        apiService: ApiService,
        tvShowMapper:TvShowMapper,
        tvShowDetailsMapper: TvShowDetailsMapper
    ): TvShowRepo{
        return TvShowRepo_Imp(
            apiService = apiService,
            tvShowMapper = tvShowMapper,
            tvShowDetailsMapper = tvShowDetailsMapper
        )
    }
}


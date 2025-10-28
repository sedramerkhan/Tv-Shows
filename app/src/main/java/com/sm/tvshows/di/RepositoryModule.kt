package com.sm.tvshows.di

import com.sm.tvshows.network.ApiService
import com.sm.tvshows.network.mapper.TvShowDetailsMapper
import com.sm.tvshows.network.mapper.TvShowMapper
import com.sm.tvshows.repository.TvShowRepo
import com.sm.tvshows.repository.TvShowRepo_Imp
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


package com.example.moviesjetpackcompose.di

import com.example.moviesjetpackcompose.network.ApiService
import com.example.moviesjetpackcompose.network.mapper.TvShowDetailsMapper
import com.example.moviesjetpackcompose.network.mapper.TvShowMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideTvShows(): TvShowMapper {
        return TvShowMapper()
    }

    @Singleton
    @Provides
    fun provideTvShowsDetails(): TvShowDetailsMapper {
        return TvShowDetailsMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://www.episodate.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
    }
}

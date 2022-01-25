package com.example.moviesjetpackcompose.network.mapper

import com.example.moviesjetpackcompose.domain.model.TvShowDetails
import com.example.moviesjetpackcompose.domain.utils.DomainMapper
import com.example.moviesjetpackcompose.network.model.TvShowDetailsDto

class TvShowDetailsMapper : DomainMapper<TvShowDetailsDto, TvShowDetails> {

    var episodesMapper = EpisodesMapper()

    override fun mapToDomainModel(model: TvShowDetailsDto): TvShowDetails {

        var episodes = episodesMapper.toDomainList(model.episodes)
        return TvShowDetails(
            id = model.id,
            name = model.name,
            permalink = model.permalink,
            url = model.url,
            description = model.url,
            description_source = model.description_source,
            start_date = model.start_date,
            end_date = model.end_date,
            country = model.country,
            status = model.status,
            runtime = model.runtime,
            network = model.network,
            youtube_link = model.youtube_link,
            image_path = model.image_path,
            image_thumbnail_path = model.image_thumbnail_path,
            rating = model.rating,
            rating_count = model.rating_count,
            countdown = model.countdown,
            genres = model.genres,
            pictures = model.pictures,
            episodes = episodes
        )
    }

    override fun mapFromDomainModel(domainModel: TvShowDetails): TvShowDetailsDto {
        var episodes = episodesMapper.fromDomainList(domainModel.episodes)
        return TvShowDetailsDto(
            id = domainModel.id,
            name = domainModel.name,
            permalink = domainModel.permalink,
            url =domainModel.url,
            description =domainModel.url,
            description_source = domainModel.description_source,
            start_date = domainModel.start_date,
            end_date = domainModel.end_date,
            country = domainModel.country,
            status = domainModel.status,
            runtime = domainModel.runtime,
            network = domainModel.network,
            youtube_link = domainModel.youtube_link,
            image_path = domainModel.image_path,
            image_thumbnail_path = domainModel.image_thumbnail_path,
            rating = domainModel.rating,
            rating_count = domainModel.rating_count,
            countdown = domainModel.countdown,
            genres = domainModel.genres,
            pictures = domainModel.pictures,
            episodes = episodes
        )
    }

    fun toDomainList(initial: List<TvShowDetailsDto>): List<TvShowDetails>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<TvShowDetails>): List<TvShowDetailsDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}
package com.example.moviesjetpackcompose.network.mapper

import com.example.moviesjetpackcompose.domain.model.Episode
import com.example.moviesjetpackcompose.domain.utils.DomainMapper
import com.example.moviesjetpackcompose.network.model.EpisodeDto

class EpisodesMapper : DomainMapper<EpisodeDto, Episode> {
    override fun mapToDomainModel(model: EpisodeDto): Episode =
        Episode(
            season= model.season,
            episode= model.episode,
            name= model.name,
            air_date= model.airDate
        )



    override fun mapFromDomainModel(domainModel: Episode): EpisodeDto =
        EpisodeDto(
            season= domainModel.season,
            episode= domainModel.episode,
            name= domainModel.name,
            airDate= domainModel.air_date
        )

    fun toDomainList(initial: List<EpisodeDto>): List<Episode>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Episode>): List<EpisodeDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}
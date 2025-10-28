package com.sm.tvshows.network.mapper

import com.sm.tvshows.domain.model.Episode
import com.sm.tvshows.domain.utils.DomainMapper
import com.sm.tvshows.network.model.EpisodeDto

class EpisodesMapper : DomainMapper<EpisodeDto, Episode> {
    override fun mapToDomainModel(model: EpisodeDto): Episode =
        Episode(
            season= model.season,
            episode= model.episode,
            name= model.name,
            airDate= model.airDate
        )



    override fun mapFromDomainModel(domainModel: Episode): EpisodeDto =
        EpisodeDto(
            season= domainModel.season,
            episode= domainModel.episode,
            name= domainModel.name,
            airDate= domainModel.airDate
        )

    fun toDomainList(initial: List<EpisodeDto>): List<Episode>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Episode>): List<EpisodeDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}
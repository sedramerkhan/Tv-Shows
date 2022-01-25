package com.example.moviesjetpackcompose.network.mapper

import com.example.moviesjetpackcompose.domain.model.Episodes
import com.example.moviesjetpackcompose.domain.utils.DomainMapper
import com.example.moviesjetpackcompose.network.model.EpisodesDto

class EpisodesMapper : DomainMapper<EpisodesDto, Episodes> {
    override fun mapToDomainModel(model: EpisodesDto): Episodes =
        Episodes(
            season= model.season,
            episode= model.episode,
            name= model.name,
            air_date= model.air_date
        )



    override fun mapFromDomainModel(domainModel: Episodes): EpisodesDto =
        EpisodesDto(
            season= domainModel.season,
            episode= domainModel.episode,
            name= domainModel.name,
            air_date= domainModel.air_date
        )

    fun toDomainList(initial: List<EpisodesDto>): List<Episodes>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Episodes>): List<EpisodesDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}
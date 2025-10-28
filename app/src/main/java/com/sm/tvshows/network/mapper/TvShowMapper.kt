package com.sm.tvshows.network.mapper

import com.sm.tvshows.domain.model.TvShow
import com.sm.tvshows.domain.utils.DomainMapper
import com.sm.tvshows.network.model.TvShowDto

class TvShowMapper : DomainMapper<TvShowDto,TvShow> {
    override fun mapToDomainModel(model: TvShowDto): TvShow {
        return TvShow(
            id= model.id,
            name= model.name,
            permalink= model.permalink,
            start_date= model.start_date,
            end_date= model.end_date,
            country= model.country,
            network= model.network,
            status= model.status,
            image_thumbnail_path = model.image_thumbnail_path
        )
    }

    override fun mapFromDomainModel(domainModel: TvShow): TvShowDto {
        return TvShowDto(
            id= domainModel.id,
            name= domainModel.name,
            permalink= domainModel.permalink,
            start_date= domainModel.start_date,
            end_date= domainModel.end_date,
            country= domainModel.country,
            network= domainModel.network,
            status= domainModel.status,
            image_thumbnail_path = domainModel.image_thumbnail_path
        )
    }

    fun toDomainList(initial: List<TvShowDto>): List<TvShow>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<TvShow>): List<TvShowDto>{
        return initial.map { mapFromDomainModel(it) }
    }

}
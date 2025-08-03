package org.artificery.dramasukiutil.data.mapper

import org.artificery.dramasukiutil.data.local.entity.MovieDataCacheEntity
import org.artificery.dramasukiutil.domain.model.MovieDataCache

fun MovieDataCacheEntity.toDomain(): MovieDataCache = MovieDataCache(
    id = id,
    year = year,
    title = title,
    alternativeTitle = alternativeTitle,
    description = description,
    posterUrl = posterUrl,
    rating = rating,
    dramaSukiFolderName = dramaSukiFolderName
)

fun MovieDataCache.toEntity(): MovieDataCacheEntity = MovieDataCacheEntity(
    id = id,
    year = year,
    title = title,
    alternativeTitle = alternativeTitle,
    description = description,
    posterUrl = posterUrl,
    rating = rating,
    dramaSukiFolderName = dramaSukiFolderName
)
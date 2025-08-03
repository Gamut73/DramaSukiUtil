package org.artificery.dramasukiutil.domain.model

data class MovieDataCache(
    val id: Long,
    val year: Int,
    val title: String,
    val alternativeTitle: String,
    val description: String,
    val posterUrl: String,
    val rating: Float,
    val dramaSukiFolderName: String,
)

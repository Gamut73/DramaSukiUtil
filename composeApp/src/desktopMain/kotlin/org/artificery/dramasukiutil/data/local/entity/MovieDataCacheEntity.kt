package org.artificery.dramasukiutil.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_data_cache")
data class MovieDataCacheEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val year: Int,
    val title: String,
    val alternativeTitle: String,
    val description: String,
    val posterUrl: String,
    val rating: Float,
    @ColumnInfo(defaultValue = "")
    val dramaSukiFolderName: String,
)
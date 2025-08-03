package org.artificery.dramasukiutil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.artificery.dramasukiutil.data.local.entity.MovieDataCacheEntity

@Dao
interface MovieDataCacheDao {
    @Query("SELECT * FROM movie_data_cache")
    suspend fun getAllCachedMovieData(): List<MovieDataCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieData(movieData: MovieDataCacheEntity)

    @Query("DELETE FROM movie_data_cache WHERE id = :id")
    suspend fun deleteMovieDataById(id: Long)
}
package org.artificery.dramasukiutil.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.artificery.dramasukiutil.data.local.dao.MovieDataCacheDao
import org.artificery.dramasukiutil.data.local.entity.MovieDataCacheEntity

@Database(entities = [MovieDataCacheEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDataCacheDao(): MovieDataCacheDao
}
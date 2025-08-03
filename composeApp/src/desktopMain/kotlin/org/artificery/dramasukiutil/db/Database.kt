package org.artificery.dramasukiutil.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.artificery.dramasukiutil.data.local.dao.MovieDataCacheDao
import org.artificery.dramasukiutil.data.local.entity.MovieDataCacheEntity
import java.io.File

@Database(
    entities = [
        MovieDataCacheEntity::class
   ],
    version = 1,
    autoMigrations = [
    ]
)
abstract class DramaSukiUtilDatabase : RoomDatabase() {
    abstract fun movieDataCacheDao(): MovieDataCacheDao
}

fun getDatabaseBuilder(): RoomDatabase.Builder<DramaSukiUtilDatabase> {
    val dataHome = System.getenv("XDG_DATA_HOME")
        ?: "${System.getProperty("user.home")}/.local/share"
    val appDataDir = File(dataHome, "drama-suki-util")
    appDataDir.mkdirs()
    val dbFile = File(appDataDir, "drama_suki_util.db")
    return Room.databaseBuilder<DramaSukiUtilDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
}
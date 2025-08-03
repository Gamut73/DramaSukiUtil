package org.artificery.dramasukiutil.di

import androidx.room.RoomDatabase
import org.artificery.dramasukiutil.data.local.dao.MovieDataCacheDao
import org.artificery.dramasukiutil.data.repository.DataSukiFolderScrapper
import org.artificery.dramasukiutil.data.repository.TMDBMovieDataRepository
import org.artificery.dramasukiutil.db.DramaSukiUtilDatabase
import org.artificery.dramasukiutil.db.getDatabaseBuilder
import org.artificery.dramasukiutil.domain.repository.MovieDataRepository
import org.artificery.dramasukiutil.domain.usecase.GetMovieDataForUrlUseCase
import org.artificery.dramasukiutil.presentation.viewmodel.MainViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val desktopModule: Module = module {
    //Data
    single<RoomDatabase.Builder<DramaSukiUtilDatabase>> {
        getDatabaseBuilder()
    }
    single { DataSukiFolderScrapper() }
    single<MovieDataCacheDao> { get<DramaSukiUtilDatabase>().movieDataCacheDao() }
    single<MovieDataRepository> { TMDBMovieDataRepository(get(), get()) }

    //Domain
    single { GetMovieDataForUrlUseCase(get()) }

    //Presentation
    factory { MainViewModel(get()) }
}
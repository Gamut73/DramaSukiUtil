package org.artificery.dramasukiutil.data.repository

import app.moviebase.tmdb.Tmdb3
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import org.artificery.dramasukiutil.BuildKonfig
import org.artificery.dramasukiutil.data.local.dao.MovieDataCacheDao
import org.artificery.dramasukiutil.data.local.entity.MovieDataCacheEntity
import org.artificery.dramasukiutil.data.mapper.toDomain
import org.artificery.dramasukiutil.domain.repository.MovieDataRepository

class TMDBMovieDataRepository(
    private val dataDataSukiFolderScrapper: DataSukiFolderScrapper,
    private val movieDataCacheDao: MovieDataCacheDao
) : MovieDataRepository {

    override suspend fun getMovieDataFromUrl(
        url: String,
    ): Flow<List<MovieData>> {
        val scrapeFilesNamesFromTable = dataDataSukiFolderScrapper.scrapeFilesNamesFromTable(url).take(10)
        if (scrapeFilesNamesFromTable.isEmpty()) {
            return flowOf(emptyList())
        }

        val movieDataCacheList = movieDataCacheDao.getAllCachedMovieData()
        val movieData: List<MovieData> = scrapeFilesNamesFromTable.map { dramaSukiFile ->
            val cachedMovie = movieDataCacheList.find {
                it.dramaSukiFolderName.lowercase().trim() == dramaSukiFile.folderName.lowercase()
            }?.toDomain()
            val cachedMovieData = if (cachedMovie != null) {
                println("Found cached movie: ${cachedMovie.title} (${cachedMovie.year})")
                MovieData(
                    title = cachedMovie.title,
                    year = cachedMovie.year,
                    alternativeTitle = cachedMovie.alternativeTitle,
                    description = cachedMovie.description,
                    posterUrl = cachedMovie.posterUrl,
                    rating = cachedMovie.rating
                )
            } else {
                null
            }
            cachedMovieData ?: run {
                val searchResult = searchMovieByTitle(
                    title = dramaSukiFile.title,
                    year = dramaSukiFile.year.toInt(),
                    language = null
                ).single()
                if (searchResult.isEmpty()) {
                    MovieData(
                        title = dramaSukiFile.title,
                        year = dramaSukiFile.year.toInt(),
                        posterUrl = null,
                        rating = null,
                        description = null,
                        alternativeTitle = null
                    )
                } else {
                    val movieDBResult = searchResult.first()
                    val movieDataCacheEntity = MovieDataCacheEntity(
                        id = 0,
                        year = movieDBResult.year ?: dramaSukiFile.year.toInt(),
                        title = movieDBResult.title,
                        alternativeTitle = movieDBResult.alternativeTitle ?: "",
                        description = movieDBResult.description ?: "",
                        posterUrl = movieDBResult.posterUrl ?: "",
                        rating = movieDBResult.rating ?: 0f,
                        dramaSukiFolderName = dramaSukiFile.folderName
                    )
                    movieDataCacheDao.insertMovieData(movieDataCacheEntity)

                    movieDBResult
                }
            }
        }
        return flowOf(movieData)

    }
}

//TODO: Move this to a TMDB API client
suspend fun searchMovieByTitle(
    title: String,
    year: Int?,
    language: String?
): Flow<List<MovieData>> {
    val tmdb = Tmdb3(BuildKonfig.TMDB_API_KEY)
    val map = tmdb.search.findMovies(
        query = title,
        year = year,
        page = 1,
        includeAdult = true,
        language = language
    ).results.map { movie ->
        println("Found TMDB movie: ${movie.title} (${movie.releaseDate?.year})")
        MovieData(
            title = movie.title,
            year = movie.releaseDate?.year,
            rating = movie.voteAverage,
            description = movie.overview,
            posterUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${movie.posterPath}",
            alternativeTitle = movie.originalTitle
        )
    }
    return flow {
        emit(map)
    }
}

data class MovieData(
    val title: String,
    val year: Int?,
    val posterUrl: String? = null,
    val rating: Float? = null,
    val description: String? = null,
    val alternativeTitle: String? = null,
)
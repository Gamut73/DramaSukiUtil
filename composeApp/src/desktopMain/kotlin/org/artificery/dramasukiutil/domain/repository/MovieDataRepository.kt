package org.artificery.dramasukiutil.domain.repository

import kotlinx.coroutines.flow.Flow
import org.artificery.dramasukiutil.data.repository.MovieData

interface MovieDataRepository {
    suspend fun getMovieDataFromUrl(url: String): Flow<List<MovieData>>
}
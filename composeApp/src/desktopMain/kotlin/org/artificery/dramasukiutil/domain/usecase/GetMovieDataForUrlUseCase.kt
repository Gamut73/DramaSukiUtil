package org.artificery.dramasukiutil.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.artificery.dramasukiutil.data.repository.MovieData
import org.artificery.dramasukiutil.domain.repository.MovieDataRepository

class GetMovieDataForUrlUseCase(
    private val movieDataRepository: MovieDataRepository
) {
    suspend operator fun invoke(url: String): Flow<List<MovieData>> {
        return movieDataRepository.getMovieDataFromUrl(url = url)
    }
}
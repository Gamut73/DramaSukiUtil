package org.artificery.dramasukiutil.presentation.model

import org.artificery.dramasukiutil.data.repository.MovieData

sealed class MainScreenUIState {
    object Loading : MainScreenUIState()
    object Search: MainScreenUIState()
    data class MovieDataSuccess(val movies: List<MovieData>) : MainScreenUIState()
}
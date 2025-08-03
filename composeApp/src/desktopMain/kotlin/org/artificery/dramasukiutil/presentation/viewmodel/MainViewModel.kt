package org.artificery.dramasukiutil.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.artificery.dramasukiutil.domain.usecase.GetMovieDataForUrlUseCase
import org.artificery.dramasukiutil.presentation.model.MainScreenUIState

class MainViewModel(
    private val getMovieDataForUrlUseCase: GetMovieDataForUrlUseCase,
): ViewModel() {
    private val _uIState: MutableStateFlow<MainScreenUIState> = MutableStateFlow(MainScreenUIState.Search)
    val uIState: StateFlow<MainScreenUIState> = _uIState

    fun getMovieDataFromUrl(url: String) {
        viewModelScope.launch {
            _uIState.emit(MainScreenUIState.Loading)
            getMovieDataForUrlUseCase(url).collect { movieDataList ->
                if (movieDataList.isNotEmpty()) {
                    _uIState.value = MainScreenUIState.MovieDataSuccess(movieDataList)
                } else {
                    _uIState.value = MainScreenUIState.Search
                }
            }
        }
    }

}
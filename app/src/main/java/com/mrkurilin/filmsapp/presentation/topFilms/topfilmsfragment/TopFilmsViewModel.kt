package com.mrkurilin.filmsapp.presentation.topFilms.topfilmsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.mrkurilin.filmsapp.domain.usecase.GetFilmsPagingDataFlowUseCase
import com.mrkurilin.filmsapp.domain.usecase.ToggleFavouriteFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.ToggleWatchedFilmUseCase
import com.mrkurilin.filmsapp.presentation.topFilms.model.TopFilmUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopFilmsViewModel @Inject constructor(
    private val topFilmUiMapper: TopFilmUiMapper,
    private val toggleFavouriteFilmUseCase: ToggleFavouriteFilmUseCase,
    private val toggleWatchedFilmUseCase: ToggleWatchedFilmUseCase,
    getFilmsPagingDataFlowUseCase: GetFilmsPagingDataFlowUseCase,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<TopFilmsUIState>(TopFilmsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    val pagingFilmsFlow = getFilmsPagingDataFlowUseCase.get(viewModelScope).map { pagingData ->
        pagingData.map { topFilm ->
            topFilmUiMapper.toTopFilmUIModel(topFilm)
        }
    }

    fun toggleFavouriteFilm(filmId: Int?) {
        if (filmId != null) {
            toggleFavouriteFilmUseCase.toggle(filmId)
        }
    }

    fun toggleWatchedFilm(filmId: Int?) {
        if (filmId != null) {
            toggleWatchedFilmUseCase.toggle(filmId)
        }
    }

    fun onFilmsLoadingError(throwable: Throwable) {
        _uiStateFlow.value = TopFilmsUIState.Error(throwable)
    }

    fun onFilmsNotLoading() {
        _uiStateFlow.value = TopFilmsUIState.FilmsLoaded
    }

    fun onFilmsLoading() {
        _uiStateFlow.value = TopFilmsUIState.Loading
    }
}
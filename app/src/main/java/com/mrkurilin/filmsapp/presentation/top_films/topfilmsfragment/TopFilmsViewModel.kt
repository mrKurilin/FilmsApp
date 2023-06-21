package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.mrkurilin.filmsapp.domain.usecase.EntryFavouriteFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.EntryWatchedFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.GetFilmsPagingDataFlowUseCase
import com.mrkurilin.filmsapp.presentation.top_films.model.TopFilmUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopFilmsViewModel @Inject constructor(
    private val topFilmUiMapper: TopFilmUiMapper,
    private val entryFavouriteFilmUseCase: EntryFavouriteFilmUseCase,
    private val entryWatchedFilmUseCase: EntryWatchedFilmUseCase,
    getFilmsPagingDataFlowUseCase: GetFilmsPagingDataFlowUseCase,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<TopFilmsUIState>(TopFilmsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    val pagingFilmsFlow = getFilmsPagingDataFlowUseCase.get().map { pagingData ->
        pagingData.map { topFilm ->
            topFilmUiMapper.map(topFilm)
        }
    }.cachedIn(viewModelScope)

    fun entryFavouriteFilm(filmId: Int) {
        entryFavouriteFilmUseCase.entry(filmId)
    }

    fun entryWatchedFilm(filmId: Int) {
        entryWatchedFilmUseCase.entry(filmId)
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
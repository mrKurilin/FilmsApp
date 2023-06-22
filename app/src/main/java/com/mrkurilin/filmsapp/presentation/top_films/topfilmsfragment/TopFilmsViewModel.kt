package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.mrkurilin.filmsapp.domain.usecase.EntryFavouriteFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.EntryWatchedFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.GetFilmsPagingDataFlowUseCase
import com.mrkurilin.filmsapp.presentation.top_films.model.TopFilmUIModel
import com.mrkurilin.filmsapp.presentation.top_films.model.TopFilmUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopFilmsViewModel @Inject constructor(
    private val topFilmUiMapper: TopFilmUiMapper,
    private val toggleFavouriteFilmUseCase: EntryFavouriteFilmUseCase,
    private val toggleWatchedFilmUseCase: EntryWatchedFilmUseCase,
    getFilmsPagingDataFlowUseCase: GetFilmsPagingDataFlowUseCase,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<TopFilmsUIState>(TopFilmsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _pagingFilmsFlow = MutableStateFlow<PagingData<TopFilmUIModel>?>(null)
    val pagingFilmsFlow: StateFlow<PagingData<TopFilmUIModel>?> = _pagingFilmsFlow

    init {
        viewModelScope.launch {
            getFilmsPagingDataFlowUseCase.get(viewModelScope).map { pagingData ->
                pagingData.map { topFilm ->
                    topFilmUiMapper.toTopFilmUIModel(topFilm)
                }
            }.collectLatest { pagingData ->
                _pagingFilmsFlow.update {
                    pagingData
                }
            }
        }
    }

    fun toggleFavouriteFilm(filmId: Int?) {
        if (filmId == null) {
            // TODO: SetErrorState + show toast
        } else {
            toggleFavouriteFilmUseCase.toggle(filmId)
        }
    }

    fun toggleWatchedFilm(filmId: Int?) {
        if (filmId == null) {
            // TODO: SetErrorState + show toast
        } else {
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
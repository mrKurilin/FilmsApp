package com.mrkurilin.filmsapp.presentation.topfilmsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.data.network.PagingFilmsFlow
import com.mrkurilin.filmsapp.domain.model.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TopFilmsViewModel @Inject constructor(
    pagingFilmFlow: PagingFilmsFlow,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<TopFilmsUIState>(TopFilmsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    val pagingFilmsFlow = pagingFilmFlow.cachedIn(viewModelScope)

    fun entryFavourite(film: Film) {

    }

    fun errorOccurred(throwable: Throwable) {
        _uiStateFlow.value = TopFilmsUIState.Error(throwable)
    }

    fun filmsLoaded() {
        _uiStateFlow.value = TopFilmsUIState.FilmsLoaded
    }

    fun loadingState() {
        _uiStateFlow.value = TopFilmsUIState.Loading
    }
}
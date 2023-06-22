package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.usecase.EntryFavouriteFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.EntryWatchedFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.GetFilmDetailsFlowByIdUseCase
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsViewModel @Inject constructor(
    private val getFilmDetailsFlowByIdUseCase: GetFilmDetailsFlowByIdUseCase,
    private val filmDetailsUiMapper: FilmDetailsUiMapper,
    private val toggleFavouriteFilmUseCase: EntryFavouriteFilmUseCase,
    private val toggleWatchedFilmUseCase: EntryWatchedFilmUseCase,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailsUIState>(FilmDetailsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    suspend fun loadFilm(filmId: Int) {
        viewModelScope.launch {
            getFilmDetailsFlowByIdUseCase.get(filmId).collect { filmDetails ->
                _uiStateFlow.update {
                    FilmDetailsUIState.FilmLoaded(
                        filmDetailsUiMapper.map(filmDetails)
                    )
                }
            }
        }
    }

    fun toggleFavouriteFilm(filmId: Int) {
        toggleFavouriteFilmUseCase.toggle(filmId)
    }

    fun toggleWatchedFilm(filmId: Int) {
        toggleWatchedFilmUseCase.toggle(filmId)
    }

    fun onErrorOccurred() {
        _uiStateFlow.value = FilmDetailsUIState.Error
    }

    fun onLoading() {
        _uiStateFlow.value = FilmDetailsUIState.Loading
    }
}
package com.mrkurilin.filmsapp.presentation.filmDetails.filmdetailsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.usecase.GetFilmDetailsFlowByIdUseCase
import com.mrkurilin.filmsapp.domain.usecase.ToggleFavouriteFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.ToggleWatchedFilmUseCase
import com.mrkurilin.filmsapp.presentation.filmDetails.model.FilmDetailsUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsViewModel @Inject constructor(
    private val getFilmDetailsFlowByIdUseCase: GetFilmDetailsFlowByIdUseCase,
    private val filmDetailsUiMapper: FilmDetailsUiMapper,
    private val toggleFavouriteFilmUseCase: ToggleFavouriteFilmUseCase,
    private val toggleWatchedFilmUseCase: ToggleWatchedFilmUseCase,
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
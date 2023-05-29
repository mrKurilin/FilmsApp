package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.usecase.GetFilmDetailsByIdUseCase
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsViewModel @Inject constructor(
    private val getFilmDetailsByIdUseCase: GetFilmDetailsByIdUseCase,
    private val filmDetailsUiMapper: FilmDetailsUiMapper,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailsUIState>(FilmDetailsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun loadFilm(filmId: Int) {
        viewModelScope.launch {
            val filmDetails = getFilmDetailsByIdUseCase.get(filmId)
            val filmDetailsUiModel = filmDetailsUiMapper.map(filmDetails)
            _uiStateFlow.value = FilmDetailsUIState.FilmLoaded(filmDetailsUiModel)
        }
    }
}
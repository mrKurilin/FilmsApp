package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import androidx.lifecycle.ViewModel
import com.mrkurilin.filmsapp.domain.usecase.GetFilmDetailsByIdUseCase
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiMapper
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilmDetailsViewModel @Inject constructor(
    private val getFilmDetailsByIdUseCase: GetFilmDetailsByIdUseCase,
    private val filmDetailsUiMapper: FilmDetailsUiMapper,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailsUIState>(FilmDetailsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    suspend fun loadFilm(filmId: Int): FilmDetailsUiModel {
        val filmDetails = withContext(Dispatchers.Default) {
            getFilmDetailsByIdUseCase.get(filmId)
        }
        return filmDetailsUiMapper.map(filmDetails)
    }

    fun onFavouriteClicked(filmId: Int) {

    }

    fun onWatchedClicked(filmId: Int) {

    }

    fun onErrorOccurred() {
        _uiStateFlow.value = FilmDetailsUIState.Error
    }

    fun onGlideResourceReady() {
        _uiStateFlow.value = FilmDetailsUIState.FilmLoaded
    }

    fun onLoading() {
        _uiStateFlow.value = FilmDetailsUIState.Loading
    }
}
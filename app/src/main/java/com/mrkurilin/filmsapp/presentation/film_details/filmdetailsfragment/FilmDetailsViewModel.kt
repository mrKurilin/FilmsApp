package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.usecase.EntryFavouriteFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.EntryWatchedFilmUseCase
import com.mrkurilin.filmsapp.domain.usecase.GetFilmDetailsByIdUseCase
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiMapper
import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsViewModel @Inject constructor(
    private val getFilmDetailsByIdUseCase: GetFilmDetailsByIdUseCase,
    private val filmDetailsUiMapper: FilmDetailsUiMapper,
    private val entryFavouriteFilmUseCase: EntryFavouriteFilmUseCase,
    private val entryWatchedFilmUseCase: EntryWatchedFilmUseCase,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailsUIState>(FilmDetailsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()
    private lateinit var currentFilmDetailsUiModel: FilmDetailsUiModel

    suspend fun loadFilm(filmId: Int) {
        viewModelScope.launch {
            val filmDetails = getFilmDetailsByIdUseCase.get(filmId)
            currentFilmDetailsUiModel = filmDetailsUiMapper.map(filmDetails)
            _uiStateFlow.value = FilmDetailsUIState.FilmLoaded(currentFilmDetailsUiModel)
        }
    }

    fun entryFavouriteFilm() {
        val isCurrentFavourite = currentFilmDetailsUiModel.isFavourite
        currentFilmDetailsUiModel = currentFilmDetailsUiModel.copy(
            isFavourite = !isCurrentFavourite
        )
        entryFavouriteFilmUseCase.entry(currentFilmDetailsUiModel.filmId)
    }

    fun entryWatchedFilm() {
        val isCurrentWatched = currentFilmDetailsUiModel.isWatched
        currentFilmDetailsUiModel = currentFilmDetailsUiModel.copy(
            isWatched = !isCurrentWatched
        )
        entryWatchedFilmUseCase.entry(currentFilmDetailsUiModel.filmId)
    }

    fun onErrorOccurred() {
        _uiStateFlow.value = FilmDetailsUIState.Error
    }

    fun onLoading() {
        _uiStateFlow.value = FilmDetailsUIState.Loading
    }
}
package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiModel

sealed class FilmDetailsUIState {

    object Loading : FilmDetailsUIState()

    data class FilmLoaded(val filmDetailsUiModel: FilmDetailsUiModel) : FilmDetailsUIState()

    object Error : FilmDetailsUIState()
}
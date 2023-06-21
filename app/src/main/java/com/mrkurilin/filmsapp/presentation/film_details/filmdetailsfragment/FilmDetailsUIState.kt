package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiModel

sealed class FilmDetailsUIState {

    object Loading : FilmDetailsUIState()

    data class FilmUpdatedAndReadyToShow(
        val filmDetailsUiModel: FilmDetailsUiModel
    ) : FilmDetailsUIState()

    data class FilmLoaded(val posterUrl: String) : FilmDetailsUIState()

    object Error : FilmDetailsUIState()
}
package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

import com.mrkurilin.filmsapp.presentation.film_details.model.FilmDetailsUiModel

sealed class FilmDetailsUIState {

    object Loading : FilmDetailsUIState()

    class FilmLoaded(val filmDetailsUiModel: FilmDetailsUiModel) : FilmDetailsUIState()

    class Error(val exception: Throwable) : FilmDetailsUIState()
}
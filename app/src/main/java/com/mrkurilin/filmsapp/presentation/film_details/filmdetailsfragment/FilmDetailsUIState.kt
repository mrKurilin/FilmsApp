package com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment

sealed class FilmDetailsUIState {

    object Loading : FilmDetailsUIState()

    object FilmLoaded : FilmDetailsUIState()

    object Error : FilmDetailsUIState()
}
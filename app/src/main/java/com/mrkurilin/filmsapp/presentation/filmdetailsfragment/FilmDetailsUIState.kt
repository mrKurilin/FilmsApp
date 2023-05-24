package com.mrkurilin.filmsapp.presentation.filmdetailsfragment

import com.mrkurilin.filmsapp.domain.model.Film

sealed class FilmDetailsUIState {

    object Loading : FilmDetailsUIState()

    class FilmLoaded(val film: Film) : FilmDetailsUIState()

    class Error(val exception: Throwable) : FilmDetailsUIState()
}
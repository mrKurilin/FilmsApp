package com.mrkurilin.filmsapp.presentation.topFilms.topfilmsfragment

sealed class TopFilmsUIState {

    object Loading : TopFilmsUIState()

    object FilmsLoaded : TopFilmsUIState()

    class Error(val exception: Throwable) : TopFilmsUIState()
}
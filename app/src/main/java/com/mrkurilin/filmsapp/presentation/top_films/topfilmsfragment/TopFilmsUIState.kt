package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

sealed class TopFilmsUIState {

    object Loading : TopFilmsUIState()

    object FilmsLoaded : TopFilmsUIState()

    class Error(val exception: Throwable) : TopFilmsUIState()
}
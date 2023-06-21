package com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment

sealed class TopFilmsUIState {

    object Loading : TopFilmsUIState()

    object FilmsLoaded : TopFilmsUIState()

    class Error(val exception: Throwable) : TopFilmsUIState()

    object FilmAddedToFavourite : TopFilmsUIState()

    object FilmRemovedFromFavourite : TopFilmsUIState()

    object FilmAddedToWatched : TopFilmsUIState()

    object FilmRemovedFromWatched : TopFilmsUIState()

    object FilmEntryError : TopFilmsUIState()
}
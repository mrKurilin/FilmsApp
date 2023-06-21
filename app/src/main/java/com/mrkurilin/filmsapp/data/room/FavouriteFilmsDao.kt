package com.mrkurilin.filmsapp.data.room

import javax.inject.Inject

class FavouriteFilmsDao @Inject constructor() {

    fun isFilmFavourite(kinopoiskId: Int): Boolean {
        return false
    }

    fun entryFavouriteFilm(filmId: Int) {

    }
}
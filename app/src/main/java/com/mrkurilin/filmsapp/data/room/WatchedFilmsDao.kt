package com.mrkurilin.filmsapp.data.room

import javax.inject.Inject

class WatchedFilmsDao @Inject constructor(){

    fun isFilmWatched(filmId: Int): Boolean {
        return false
    }
}
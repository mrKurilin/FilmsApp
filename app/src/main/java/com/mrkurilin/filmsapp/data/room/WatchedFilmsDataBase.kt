package com.mrkurilin.filmsapp.data.room

interface WatchedFilmsDataBase {

    fun isFilmWatched(kinopoiskId: Int): Boolean
}
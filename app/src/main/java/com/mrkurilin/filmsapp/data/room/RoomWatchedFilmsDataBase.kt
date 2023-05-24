package com.mrkurilin.filmsapp.data.room

import javax.inject.Inject

class RoomWatchedFilmsDataBase @Inject constructor() : WatchedFilmsDataBase {

    override fun isFilmWatched(kinopoiskId: Int): Boolean {
        return false
    }
}
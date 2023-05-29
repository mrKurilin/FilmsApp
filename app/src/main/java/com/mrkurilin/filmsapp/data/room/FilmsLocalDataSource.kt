package com.mrkurilin.filmsapp.data.room

import javax.inject.Inject

class FilmsLocalDataSource @Inject constructor(
    private val watchedFilmsDao: WatchedFilmsDao,
    private val favouriteFilmsDao: FavouriteFilmsDao,
) {

    fun isFilmFavourite(filmId: Int): Boolean {
        return watchedFilmsDao.isFilmWatched(filmId)
    }

    fun isFilmWatched(filmId: Int): Boolean {
        return favouriteFilmsDao.isFilmFavourite(filmId)
    }
}
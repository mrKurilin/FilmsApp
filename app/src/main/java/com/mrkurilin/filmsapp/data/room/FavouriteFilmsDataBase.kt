package com.mrkurilin.filmsapp.data.room

interface FavouriteFilmsDataBase {

    fun isFilmFavourite(kinopoiskId: Int): Boolean
}
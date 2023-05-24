package com.mrkurilin.filmsapp.data.room

import javax.inject.Inject

class RoomFavouriteFilmsDataBase @Inject constructor(): FavouriteFilmsDataBase {

    override fun isFilmFavourite(kinopoiskId: Int): Boolean {
        return false
    }
}
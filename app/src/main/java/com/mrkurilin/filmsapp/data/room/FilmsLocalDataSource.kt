package com.mrkurilin.filmsapp.data.room

import com.mrkurilin.filmsapp.data.room.model.FilmStatusLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsLocalDataSource @Inject constructor(
    private val filmLocalDao: FilmLocalDao,
) {

    fun getFilmsStatusLocalListFlow(): Flow<List<FilmStatusLocal>> {
        return filmLocalDao.getFilmsStatusLocalListFlow()
    }

    fun getFilmStatusLocal(filmId: Int): FilmStatusLocal? {
        return filmLocalDao.getFilmStatusLocal(filmId)
    }

    fun insertFilmStatusLocal(filmStatusLocal: FilmStatusLocal) {
        filmLocalDao.insertFilmStatusLocal(filmStatusLocal)
    }

    fun updateFilmStatusLocal(filmStatusLocal: FilmStatusLocal) {
        filmLocalDao.updateFilmStatusLocal(filmStatusLocal)
    }

    fun getFilmStatusLocalFlow(filmId: Int): Flow<FilmStatusLocal?> {
        return filmLocalDao.getFilmStatusLocalFlow(filmId)
    }
}
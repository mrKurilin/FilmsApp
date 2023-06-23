package com.mrkurilin.filmsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.filmIdColumnName
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.filmStatusLocalTableName
import com.mrkurilin.filmsapp.data.room.model.FilmStatusLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmLocalDao {

    @Query("SELECT * FROM $filmStatusLocalTableName")
    fun getFilmsStatusLocalListFlow(): Flow<List<FilmStatusLocal>>

    @Query("SELECT * FROM $filmStatusLocalTableName WHERE $filmIdColumnName = :filmId")
    fun getFilmStatusLocal(filmId: Int): FilmStatusLocal?

    @Query("SELECT * FROM $filmStatusLocalTableName WHERE $filmIdColumnName = :filmId")
    fun getFilmStatusLocalFlow(filmId: Int): Flow<FilmStatusLocal?>

    @Insert
    fun insertFilmStatusLocal(filmStatusLocal: FilmStatusLocal)

    @Update
    fun updateFilmStatusLocal(filmStatusLocal: FilmStatusLocal)
}
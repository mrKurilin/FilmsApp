package com.mrkurilin.filmsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.filmIdColumnName
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.filmStatusLocalTableName
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.isFavouriteColumnName
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.isWatchedColumnName
import com.mrkurilin.filmsapp.data.room.model.FilmStatusLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmLocalDao {

    @Query("SELECT * FROM $filmStatusLocalTableName")
    fun getFilmsStatusLocalListFlow(): Flow<List<FilmStatusLocal>>

    @Query("UPDATE $filmStatusLocalTableName SET $isWatchedColumnName = NOT $isWatchedColumnName WHERE $filmIdColumnName = :filmId")
    fun toggleWatchedFilmLocal(filmId: Int)

    @Query("UPDATE $filmStatusLocalTableName SET $isFavouriteColumnName = NOT $isFavouriteColumnName WHERE $filmIdColumnName = :filmId")
    fun toggleFavouriteFilmLocal(filmId: Int)

    @Query("SELECT * FROM $filmStatusLocalTableName WHERE $filmIdColumnName = :filmId")
    fun getFilmStatusLocal(filmId: Int): FilmStatusLocal?

    @Query("SELECT * FROM $filmStatusLocalTableName WHERE $filmIdColumnName = :filmId")
    fun getFilmStatusLocalFlow(filmId: Int): Flow<FilmStatusLocal?>

    @Query("SELECT $filmIdColumnName FROM $filmStatusLocalTableName WHERE $isWatchedColumnName = 1")
    fun getWatchedFilmIdsListFlow(): Flow<List<Int>>

    @Query("SELECT $filmIdColumnName FROM $filmStatusLocalTableName WHERE $isFavouriteColumnName = 1")
    fun getFavouriteFilmIdsListFlow(): Flow<List<Int>>

    @Insert
    fun insertFilmStatusLocal(filmStatusLocal: FilmStatusLocal)

    @Update
    fun updateFilmStatusLocal(filmStatusLocal: FilmStatusLocal)
}
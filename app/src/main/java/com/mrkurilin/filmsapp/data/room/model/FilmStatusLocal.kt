package com.mrkurilin.filmsapp.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.filmIdColumnName
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.filmStatusLocalTableName
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.isFavouriteColumnName
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.isWatchedColumnName

@Entity(tableName = filmStatusLocalTableName)
data class FilmStatusLocal(
    @PrimaryKey @ColumnInfo(name = filmIdColumnName) val filmId: Int,
    @ColumnInfo(name = isFavouriteColumnName) val isFavourite: Boolean = false,
    @ColumnInfo(name = isWatchedColumnName) val isWatched: Boolean = false,
)
package com.mrkurilin.filmsapp.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal.Companion.filmDetailsLocalTableName

@Entity(tableName = filmDetailsLocalTableName)
data class FilmDetailsLocal(
    @PrimaryKey @ColumnInfo(name = filmIdColumnName) val filmId: Int,
    @ColumnInfo(name = nameColumnName) val name: String,
    @ColumnInfo(name = countriesColumnName) val countries: List<String>? = null,
    @ColumnInfo(name = genresColumnName) val genres: List<String>? = null,
    @ColumnInfo(name = yearColumnName) val year: Int? = null,
    @ColumnInfo(name = isFavouriteColumnName) val isFavourite: Boolean? = null,
    @ColumnInfo(name = descriptionColumnName) val description: String? = null,
    @ColumnInfo(name = posterUrlColumnName) val posterUrl: String? = null,
) {

    companion object {

        const val filmStatusLocalTableName = "film_status_table"
        const val filmDetailsLocalTableName = "film_details_table"
        const val filmIdColumnName = "film_id"
        const val nameColumnName = "name"
        const val countriesColumnName = "countries"
        const val genresColumnName = "genres"
        const val yearColumnName = "year"
        const val isFavouriteColumnName = "is_favourite"
        const val isWatchedColumnName = "is_watched"
        const val descriptionColumnName = "description"
        const val posterUrlColumnName = "poster_url"
    }
}
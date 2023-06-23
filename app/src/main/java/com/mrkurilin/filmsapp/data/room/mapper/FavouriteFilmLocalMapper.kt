package com.mrkurilin.filmsapp.data.room.mapper

import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal
import com.mrkurilin.filmsapp.domain.model.FilmDetails
import javax.inject.Inject

class FavouriteFilmLocalMapper @Inject constructor() {

    fun fromFilmDetails(filmDetails: FilmDetails): FilmDetailsLocal {
        return FilmDetailsLocal(
            filmId = filmDetails.filmId,
            name = filmDetails.name,
            countries = filmDetails.countries,
            genres = filmDetails.genres,
            year = filmDetails.year,
            isFavourite = filmDetails.isFavourite,
            description = filmDetails.description,
            posterUrl = filmDetails.posterUrl,
        )
    }
}
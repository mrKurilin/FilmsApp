package com.mrkurilin.filmsapp.presentation.film_details.model

import com.mrkurilin.filmsapp.domain.model.FilmDetails
import javax.inject.Inject

class FilmDetailsUiMapper @Inject constructor() {

    fun map(filmDetails: FilmDetails): FilmDetailsUiModel {
        return FilmDetailsUiModel(
            filmId = filmDetails.filmId,
            name = filmDetails.name,
            countries = filmDetails.countries.joinToString(", "),
            genres = filmDetails.genres.joinToString(", "),
            year = filmDetails.year,
            isFavourite = filmDetails.isFavourite,
            isWatched = filmDetails.isWatched,
            description = filmDetails.description,
            posterUrl = filmDetails.posterUrl,
        )
    }
}
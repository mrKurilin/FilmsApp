package com.mrkurilin.filmsapp.presentation.topFilms.model

import com.mrkurilin.filmsapp.domain.model.TopFilm
import javax.inject.Inject

class TopFilmUiMapper @Inject constructor() {

    fun toTopFilmUIModel(topFilm: TopFilm): TopFilmUIModel {
        return TopFilmUIModel(
            filmId = topFilm.filmId,
            name = topFilm.name,
            countries = topFilm.countries.joinToString(", "),
            genres = topFilm.genres.joinToString(", "),
            year = topFilm.year,
            posterUrl = topFilm.posterUrl,
            isFavourite = topFilm.isFavourite,
            isWatched = topFilm.isWatched,
        )
    }

    fun toTopFilm(topFilmUIModel: TopFilmUIModel): TopFilm {
        return TopFilm(
            filmId = topFilmUIModel.filmId,
            name = topFilmUIModel.name,
            countries = topFilmUIModel.countries.split(" "),
            genres = topFilmUIModel.genres.split(" "),
            year = topFilmUIModel.year,
            posterUrl = topFilmUIModel.posterUrl,
            isFavourite = topFilmUIModel.isFavourite,
            isWatched = topFilmUIModel.isWatched,
        )
    }
}
package com.mrkurilin.filmsapp.presentation.top_films.model

import com.mrkurilin.filmsapp.domain.model.TopFilm
import javax.inject.Inject

class TopFilmUiMapper @Inject constructor(){
    
    fun map(topFilm: TopFilm): TopFilmUIModel {
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
}
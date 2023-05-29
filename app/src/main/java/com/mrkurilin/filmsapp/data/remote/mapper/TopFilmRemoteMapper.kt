package com.mrkurilin.filmsapp.data.remote.mapper

import com.mrkurilin.filmsapp.data.remote.model.TopFilmRemote
import com.mrkurilin.filmsapp.domain.model.TopFilm
import javax.inject.Inject

class TopFilmRemoteMapper @Inject constructor() {

    fun map(
        topFilmRemote: TopFilmRemote,
        isFavourite: Boolean,
        isWatched: Boolean,
    ): TopFilm {
        val countries = topFilmRemote.countries.map { countryRemote ->
            countryRemote.country
        }
        val genres = topFilmRemote.genres.map { genreRemote ->
            genreRemote.genre
        }

        return TopFilm(
            filmId = topFilmRemote.filmId,
            name = topFilmRemote.nameRu,
            countries = countries,
            genres = genres,
            year = topFilmRemote.year,
            posterUrl = topFilmRemote.posterUrl,
            isFavourite = isFavourite,
            isWatched = isWatched,
        )
    }
}
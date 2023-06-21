package com.mrkurilin.filmsapp.data.remote.mapper

import com.mrkurilin.filmsapp.data.remote.model.FilmDetailsRemote
import com.mrkurilin.filmsapp.domain.model.FilmDetails
import javax.inject.Inject

class FilmDetailsRemoteMapper @Inject constructor() {

    fun map(
        filmDetailsRemote: FilmDetailsRemote,
        isFavourite: Boolean,
        isWatched: Boolean,
    ): FilmDetails {
        val countries = filmDetailsRemote.countries?.map { countryRemote ->
            countryRemote.country
        } ?: listOf()

        val genres = filmDetailsRemote.genres?.map { genreRemote ->
            genreRemote.genre
        } ?: listOf()

        return FilmDetails(
            filmId = filmDetailsRemote.kinopoiskId,
            name = filmDetailsRemote.nameRu ?: "",
            countries = countries,
            genres = genres,
            year = filmDetailsRemote.year,
            posterUrl = filmDetailsRemote.posterUrl,
            isFavourite = isFavourite,
            isWatched = isWatched,
            description = filmDetailsRemote.description ?: ""
        )
    }
}
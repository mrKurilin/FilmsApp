package com.mrkurilin.filmsapp.data.remote.mapper

import com.mrkurilin.filmsapp.data.remote.model.FilmRemote
import com.mrkurilin.filmsapp.domain.model.FilmDetails
import javax.inject.Inject

class FilmRemoteMapper @Inject constructor() {

    fun map(
        filmRemote: FilmRemote,
        isFavourite: Boolean,
        isWatched: Boolean,
    ): FilmDetails {
        val countries = filmRemote.countries?.map { countryRemote ->
            countryRemote.country
        } ?: listOf()

        val genres = filmRemote.genres?.map { genreRemote ->
            genreRemote.genre
        } ?: listOf()

        return FilmDetails(
            filmId = filmRemote.kinopoiskId,
            name = filmRemote.nameRu ?: "",
            countries = countries,
            genres = genres,
            year = filmRemote.year,
            posterUrl = filmRemote.posterUrl,
            isFavourite = isFavourite,
            isWatched = isWatched,
            description = filmRemote.description ?: ""
        )
    }
}
package com.mrkurilin.filmsapp.data.remote.model

data class TopFilmRemote(
    val countries: List<CountryRemote>,
    val filmId: Int,
    val filmLength: String,
    val genres: List<GenreRemote>,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val year: Int,
)
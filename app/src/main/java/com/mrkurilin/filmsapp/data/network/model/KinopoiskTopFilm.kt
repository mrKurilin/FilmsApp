package com.mrkurilin.filmsapp.data.network.model

data class KinopoiskTopFilm(
    val countries: List<Country>,
    val filmId: Int,
    val filmLength: String,
    val genres: List<Genre>,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val year: Int,
)
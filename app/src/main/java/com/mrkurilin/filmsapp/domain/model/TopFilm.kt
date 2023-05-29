package com.mrkurilin.filmsapp.domain.model

class TopFilm(
    val filmId: Int,
    val name: String,
    val countries: List<String>,
    val genres: List<String>,
    val year: Int,
    val isFavourite: Boolean,
    val isWatched: Boolean,
    val posterUrl: String
)
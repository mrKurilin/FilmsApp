package com.mrkurilin.filmsapp.domain.model

data class FilmDetails(
    val filmId: Int,
    val name: String,
    val countries: List<String>,
    val genres: List<String>,
    val year: Int,
    val isFavourite: Boolean,
    val isWatched: Boolean,
    val description: String,
    val posterUrl: String,
)
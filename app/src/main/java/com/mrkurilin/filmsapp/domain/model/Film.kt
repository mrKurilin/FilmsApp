package com.mrkurilin.filmsapp.domain.model

data class Film(
    val filmId: Int,
    val name: String,
    val countries: String,
    val genres: String,
    val year: Int,
    val isFavourite: Boolean,
    val isWatched: Boolean,
    val description: String,
    val posterUrl: String
)
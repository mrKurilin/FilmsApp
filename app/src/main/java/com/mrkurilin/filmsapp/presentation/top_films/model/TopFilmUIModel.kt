package com.mrkurilin.filmsapp.presentation.top_films.model

data class TopFilmUIModel(
    val filmId: Int,
    val name: String,
    val countries: String,
    val genres: String,
    val year: Int,
    val isFavourite: Boolean,
    val isWatched: Boolean,
    val posterUrl: String
)
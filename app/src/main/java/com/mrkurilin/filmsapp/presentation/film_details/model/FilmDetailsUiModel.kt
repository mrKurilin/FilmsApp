package com.mrkurilin.filmsapp.presentation.film_details.model

data class FilmDetailsUiModel(
    val filmId: Int,
    val name: String,
    val countries: String,
    val genres: String,
    val year: Int,
    val isFavourite: Boolean,
    val isWatched: Boolean,
    val description: String,
    val posterUrl: String,
)
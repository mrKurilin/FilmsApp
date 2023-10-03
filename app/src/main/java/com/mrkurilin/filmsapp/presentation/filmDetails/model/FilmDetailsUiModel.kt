package com.mrkurilin.filmsapp.presentation.filmDetails.model

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
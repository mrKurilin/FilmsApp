package com.mrkurilin.filmsapp.data.network.model

data class KinopoiskTopFilms(
    val films: List<KinopoiskTopFilm>,
    val pagesCount: Int
)
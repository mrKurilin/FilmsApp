package com.mrkurilin.filmsapp.data.remote.model

data class TopFilmsRemote(
    val films: List<TopFilmRemote>,
    val pagesCount: Int
)
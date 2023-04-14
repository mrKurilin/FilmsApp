package com.mrkurilin.filmsapp.domain.model

data class Genre(
    val genre: String
) {

    override fun toString(): String {
        return genre
    }
}
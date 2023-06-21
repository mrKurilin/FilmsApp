package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.data.FilmsRepository
import com.mrkurilin.filmsapp.domain.model.FilmDetails
import javax.inject.Inject

class GetFilmDetailsByIdUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
) {

    suspend fun get(filmId: Int): FilmDetails {
        return filmsRepository.getFilmDetailsById(filmId)
    }
}